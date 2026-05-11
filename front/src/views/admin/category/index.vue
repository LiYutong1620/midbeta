<template>
  <div class="space-y-6">
    <div class="flex justify-between items-center">
      <div>
        <h1 class="text-2xl font-bold text-slate-800">分类管理</h1>
        <p class="text-slate-500 mt-1">管理新闻资讯的分类层级</p>
      </div>
      <button 
        @click="handleAdd"
        class="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-lg flex items-center transition-colors shadow-lg shadow-indigo-600/30"
      >
        <Plus class="w-4 h-4 mr-2" />
        新增分类
      </button>
    </div>

    <div class="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
      <el-table 
        v-loading="loading"
        :data="categoryList" 
        style="width: 100%"
        row-key="id"
        :tree-props="{ children: 'children' }"
      >
        <el-table-column prop="categoryName" label="分类名称" min-width="200" />
        <el-table-column prop="sort" label="排序权重" width="100" align="center" />
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="{ row }">
            <span :class="row.status === 0 ? 'text-emerald-600 bg-emerald-50' : 'text-slate-400 bg-slate-50'" class="px-2 py-1 rounded text-xs font-bold uppercase">
              {{ row.status === 0 ? '启用' : '禁用' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <div class="flex space-x-2">
              <button @click="handleEdit(row)" class="text-indigo-600 hover:text-indigo-800 font-medium text-sm">编辑</button>
              <button @click="handleDelete(row)" class="text-rose-600 hover:text-rose-800 font-medium text-sm">删除</button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- Edit Dialog -->
    <el-dialog
      v-model="dialogVisible"
      :title="form.id ? '编辑分类' : '新增分类'"
      width="500px"
      destroy-on-close
    >
      <el-form :model="form" label-width="80px" class="mt-4">
        <el-form-item label="父级分类">
          <el-tree-select
            v-model="form.parentId"
            :data="treeOptions"
            check-strictly
            :render-after-expand="false"
            placeholder="不选则为顶级分类"
            class="w-full"
          />
        </el-form-item>
        <el-form-item label="分类名称" required>
          <el-input v-model="form.categoryName" placeholder="输入分类名称" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" class="w-full" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="0">启用</el-radio>
            <el-radio :label="1">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="flex justify-end space-x-3">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm" :loading="submitLoading">提交</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { Plus } from 'lucide-vue-next';
import { ElMessage, ElMessageBox } from 'element-plus';
import request from '@/utils/request';

const loading = ref(false);
const submitLoading = ref(false);
const dialogVisible = ref(false);
const categoryList = ref([]);
const treeOptions = ref([]);

const form = ref({
  id: null,
  parentId: 0,
  categoryName: '',
  sort: 0,
  status: 0
});

const getList = async () => {
  loading.value = true;
  try {
    const res = await request.get('/system/category/list');
    // Assuming backend returns flat list and we treeify or it's already treeified
    // OpenAPI NewsCategory has 'children' property, usually list returns flat.
    // If it's flat, we need a helper to build tree.
    categoryList.value = res.data || [];
    
    // Build tree options for parent selector
    treeOptions.value = [{ id: 0, categoryName: '顶级分类', children: transformToTree(res.data) }];
  } catch (error) {
    console.error('Failed to load categories');
  } finally {
    loading.value = false;
  }
};

const transformToTree = (data) => {
  if (!data) return [];
  const map = {};
  data.forEach(item => {
    map[item.id] = { ...item, label: item.categoryName, value: item.id, children: [] };
  });
  const tree = [];
  data.forEach(item => {
    const node = map[item.id];
    if (item.parentId && map[item.parentId]) {
      map[item.parentId].children.push(node);
    } else {
      tree.push(node);
    }
  });
  return tree;
};

onMounted(() => {
  getList();
});

const handleAdd = () => {
  form.value = {
    id: null,
    parentId: 0,
    categoryName: '',
    sort: 0,
    status: 0
  };
  dialogVisible.value = true;
};

const handleEdit = (row) => {
  form.value = { ...row };
  dialogVisible.value = true;
};

const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该分类及其子分类吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await request.delete(`/system/category/${row.id}`);
    ElMessage.success('删除成功');
    getList();
  });
};

const submitForm = async () => {
  if (!form.value.categoryName) return ElMessage.error('请输入分类名称');
  submitLoading.value = true;
  try {
    if (form.value.id) {
      await request.put('/system/category', form.value);
    } else {
      await request.post('/system/category', form.value);
    }
    ElMessage.success('操作成功');
    dialogVisible.value = false;
    getList();
  } finally {
    submitLoading.value = false;
  }
};
</script>
