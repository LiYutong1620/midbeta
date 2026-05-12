<template>
  <el-drawer
    v-model="visible"
    :title="title"
    size="50%"
    :close-on-click-modal="false"
    class="news-drawer"
  >
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-position="top"
      class="p-4"
    >
      <el-form-item label="新闻标题 TITLE" prop="title">
        <el-input v-model="form.title" placeholder="请输入新闻标题" class="geometric-input" />
      </el-form-item>

      <!-- 封面图上传区域 -->
      <el-form-item label="封面图片 COVER">
        <el-upload
          class="cover-uploader"
          :action="uploadUrl"
          :headers="uploadHeaders"
          :show-file-list="false"
          :on-success="handleCoverSuccess"
          :before-upload="beforeCoverUpload"
        >
          <img v-if="form.coverUrl" :src="getFullUrl(form.coverUrl)" class="cover-image" />
          <div v-else class="upload-box">
            <Plus class="w-8 h-8 text-slate-400" />
            <span class="text-xs text-slate-400 mt-1">上传封面</span>
          </div>
        </el-upload>
      </el-form-item>

      <div class="grid grid-cols-2 gap-4">
        <el-form-item label="所属分类 CATEGORY" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类" class="w-full geometric-select">
            <el-option
              v-for="cat in categories"
              :key="cat.id"
              :label="cat.categoryName"
              :value="cat.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="发布状态 STATUS" prop="publishStatus">
          <el-radio-group v-model="form.publishStatus" class="flex space-x-4">
            <el-radio :label="0">草稿</el-radio>
            <el-radio :label="1">发布</el-radio>
          </el-radio-group>
        </el-form-item>
      </div>

      <el-form-item label="标签 TAGS">
        <el-select v-model="form.tagIds" multiple placeholder="请选择标签" class="w-full geometric-select">
          <el-option
            v-for="tag in tags"
            :key="tag.id"
            :label="tag.tagName"
            :value="tag.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="摘要 SUMMARY" prop="summary">
        <el-input
          v-model="form.summary"
          type="textarea"
          :rows="3"
          placeholder="请输入新闻摘要"
          class="geometric-input"
        />
      </el-form-item>

      <el-form-item label="新闻正文 CONTENT" prop="content">
        <div class="border border-slate-200 rounded-xl overflow-hidden quill-container">
          <QuillEditor
            v-model:content="form.content"
            content-type="html"
            theme="snow"
            :options="editorOptions"
            style="height: 300px;"
          />
        </div>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="flex items-center justify-end space-x-4 px-4 py-4 border-t border-slate-100">
        <button
          class="px-6 py-2 border border-slate-200 text-slate-400 font-bold uppercase tracking-widest text-[11px] rounded-lg hover:bg-slate-50 transition-colors"
          @click="visible = false"
        >
          取消
        </button>
        <button
          :disabled="submitting"
          class="px-8 py-2 bg-indigo-600 text-white rounded-lg text-[11px] font-bold uppercase tracking-widest shadow-lg shadow-indigo-600/20 hover:bg-indigo-700 transition-all disabled:opacity-50"
          @click="handleSubmit"
        >
          {{ submitting ? '保存中...' : '提交数据' }}
        </button>
      </div>
    </template>
  </el-drawer>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue';
import { QuillEditor } from '@vueup/vue-quill';
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import { ElMessage, type FormInstance } from 'element-plus';
import { Plus } from 'lucide-vue-next';
import request, { AjaxResult } from '@/utils/request';
import { getFullUrl } from '@/utils/image';

const emit = defineEmits(['success']);

const visible = ref(false);
const submitting = ref(false);
const formRef = ref<FormInstance>();
const categories = ref<any[]>([]);
const tags = ref<any[]>([]);

const form = reactive<any>({
  id: undefined,
  title: '',
  summary: '',
  categoryId: undefined,
  content: '',
  coverUrl: '',          // 新增封面字段
  publishStatus: 1,
  tagIds: [],
});

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  content: [{ required: true, message: '请输入正文', trigger: 'blur' }]
};

const title = computed(() => (form.id ? '编辑新闻/UPDATE NEWS' : '发布新闻/CREATE NEWS'));

const editorOptions = {
  placeholder: '开始编写新闻内容...',
  modules: {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike'],
      ['blockquote', 'code-block'],
      [{ 'header': 1 }, { 'header': 2 }],
      [{ 'list': 'ordered' }, { 'list': 'bullet' }],
      [{ 'color': [] }, { 'background': [] }],
      ['clean'],
      ['link']
    ]
  }
};

// 上传相关
const uploadUrl = computed(() => import.meta.env.VITE_APP_BASE_API + '/common/upload');

const uploadHeaders = computed(() => {
  const token = localStorage.getItem('token');
  return token ? { Authorization: `Bearer ${token}` } : {};
});

const handleCoverSuccess = (response: any) => {
  // AjaxResult.success 返回的 data 是 url 字符串
  form.coverUrl = response.data || response.url || '';
};

const beforeCoverUpload = (file: File) => {
  const isImage = file.type.startsWith('image/');
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isImage) {
    ElMessage.error('只能上传 JPG/PNG 格式的图片!');
  }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过 2MB!');
  }
  return isImage && isLt2M;
};

const open = (id?: number) => {
  reset();
  getCategoryList();
  getTagList();
  if (id) {
    getDetail(id);
  }
  visible.value = true;
};

const reset = () => {
  form.id = undefined;
  form.title = '';
  form.summary = '';
  form.categoryId = undefined;
  form.content = '';
  form.coverUrl = '';    // 重置封面
  form.publishStatus = 1;
  form.tagIds = [];
  formRef.value?.resetFields();
};

const getCategoryList = async () => {
  try {
    const res: any = await request.get('/system/category/list');
    let list: any[] = [];
    if (Array.isArray(res)) {
      list = res;
    } else if (res.data && Array.isArray(res.data)) {
      list = res.data;
    } else if (res.rows && Array.isArray(res.rows)) {
      list = res.rows;
    }
    // 只保留启用的分类（status === 1）
    categories.value = list.filter((cat: any) => cat.status == 1);
  } catch (error) {
    console.error('Failed to get categories:', error);
  }
};

const getTagList = async () => {
  try {
    const res: any = await request.get('/system/tag/list');
    tags.value = res.rows || res.data || (Array.isArray(res) ? res : []);
  } catch (error) {
    console.error('Failed to get tags:', error);
  }
};

const getDetail = async (id: number) => {
  try {
    const res: any = await request.get(`/system/news/${id}`);
    const data = res.data || res;
    form.id = data.id;
    form.title = data.title;
    form.summary = data.summary;
    form.categoryId = data.categoryId;
    form.content = data.content;
    form.coverUrl = data.coverUrl || '';    // 回显封面
    form.publishStatus = data.publishStatus;
    form.tagIds = [];
    const tagRes: any = await request.get('/system/tagRel/list', { params: { newsId: id } });
    const tagRels = tagRes.rows || tagRes.data || [];
    form.tagIds = tagRels.map((r: any) => r.tagId);
  } catch (error) {
    console.error('Failed to get news detail:', error);
  }
};

const handleSubmit = async () => {
  if (!formRef.value) return;
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true;
      try {
        const payload = { ...form };
        delete payload.tagIds;
        let newsId = form.id;
        if (form.id) {
          await request.put('/system/news', payload);
        } else {
          const createRes: any = await request.post('/system/news', payload);
          newsId = createRes.data?.id || createRes.id || form.id;
        }
        if (newsId) {
          await syncTagRels(newsId, form.tagIds);
        }
        visible.value = false;
        emit('success');
        ElMessage.success(form.id ? '更新成功' : '发布成功');
      } catch (error) {
        console.error('Save failed:', error);
      } finally {
        submitting.value = false;
      }
    }
  });
};

const syncTagRels = async (newsId: number, tagIds: number[]) => {
  try {
    const oldRes: any = await request.get('/system/tagRel/list', { params: { newsId } });
    const oldRels: any[] = oldRes.rows || oldRes.data || [];
    const oldTagIds = oldRels.map((r: any) => r.tagId);
    const toAdd = tagIds.filter((t: number) => !oldTagIds.includes(t));
    const toRemove = oldRels.filter((r: any) => !tagIds.includes(r.tagId));
    for (const tagId of toAdd) {
      await request.post('/system/tagRel', { newsId, tagId });
    }
    for (const rel of toRemove) {
      await request.delete(`/system/tagRel/${rel.id}`);
    }
  } catch (e) {
    console.error('Sync tag rels failed:', e);
  }
};

defineExpose({ open });
</script>

<style>
.news-drawer .el-drawer__header {
  margin-bottom: 0px;
  padding: 24px;
  border-bottom: 1px solid #f1f5f9;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  color: #1e293b;
}

.quill-container .ql-toolbar {
  border: none !important;
  border-bottom: 1px solid #f1f5f9 !important;
  background: #f8fafc;
}

.quill-container .ql-container {
  border: none !important;
  font-family: inherit;
}

.geometric-select .el-input__wrapper {
  background-color: #f8fafc !important;
  border: 1px solid #e2e8f0 !important;
  box-shadow: none !important;
  border-radius: 10px !important;
}

/* 封面上传样式 */
.cover-uploader .cover-image {
  width: 178px;
  height: 178px;
  object-fit: cover;
  border-radius: 8px;
}

.upload-box {
  width: 178px;
  height: 178px;
  border: 2px dashed #cbd5e1;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: border-color 0.2s;
}
.upload-box:hover {
  border-color: #4f46e5;
}
</style>