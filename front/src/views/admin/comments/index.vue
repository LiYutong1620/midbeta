<template>
  <div class="space-y-6 pb-12">
    <!-- Header/Filter Area -->
    <div class="bg-white border border-slate-200 rounded-xl shadow-sm p-6 flex flex-col md:flex-row md:items-center justify-between gap-4">
      <div>
        <h3 class="font-bold text-slate-800 text-lg">评论审核管理</h3>
        <p class="text-xs text-slate-400 mt-1 uppercase tracking-wider font-bold">Audit and moderate user comments</p>
      </div>
      <div class="flex items-center space-x-3">
        <el-select
          v-model="queryParams.auditStatus"
          placeholder="审核状态"
          class="!w-40 geometric-select"
          clearable
          @change="getList"
        >
          <el-option label="全部" :value="null" />
          <el-option label="待审核" :value="0" />
          <el-option label="通过" :value="1" />
          <el-option label="拒绝" :value="2" />
        </el-select>
        <el-input
          v-model="queryParams.content"
          placeholder="搜索评论内容"
          class="!w-64"
          clearable
          @keyup.enter="getList"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
      </div>
    </div>

    <!-- Table Area -->
    <div class="bg-white border border-slate-200 rounded-xl shadow-sm overflow-hidden flex flex-col min-h-[400px]">
      <div class="flex-1 overflow-x-auto">
        <el-table
          v-loading="loading"
          :data="commentList"
          style="width: 100%"
          class="!text-slate-600"
        >
          <el-table-column label="ID" prop="id" width="80" align="center">
            <template #default="scope">
              <span class="font-mono text-xs text-slate-400">#{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column label="新闻标题" prop="newsTitle" min-width="150" show-overflow-tooltip>
            <template #default="scope">
              <span class="text-xs font-bold text-slate-500 uppercase tracking-tight">{{ scope.row.newsTitle }}</span>
            </template>
          </el-table-column>
          <el-table-column label="评论人" prop="nickName" width="120">
            <template #default="scope">
              <div class="flex items-center space-x-2">
                <div class="w-6 h-6 bg-slate-100 rounded-full flex items-center justify-center text-[10px] font-bold text-slate-400 border border-slate-200 uppercase">
                  {{ scope.row.nickName ? scope.row.nickName.charAt(0) : 'U' }}
                </div>
                <span class="font-bold text-sm text-slate-700">{{ scope.row.nickName }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="评论内容" prop="content" min-width="200" show-overflow-tooltip>
            <template #default="scope">
              <p class="text-sm text-slate-600">{{ scope.row.content }}</p>
            </template>
          </el-table-column>
          <el-table-column label="状态" width="100" align="center">
            <template #default="scope">
              <span v-if="scope.row.auditStatus === 0" class="px-2 py-0.5 bg-orange-50 text-orange-600 text-[10px] font-bold rounded uppercase tracking-wider ring-1 ring-orange-100">
                待审核
              </span>
              <span v-else-if="scope.row.auditStatus === 1" class="px-2 py-0.5 bg-emerald-50 text-emerald-600 text-[10px] font-bold rounded uppercase tracking-wider ring-1 ring-emerald-100">
                通过
              </span>
              <span v-else class="px-2 py-0.5 bg-red-50 text-red-600 text-[10px] font-bold rounded uppercase tracking-wider ring-1 ring-red-100">
                驳回
              </span>
            </template>
          </el-table-column>
          <el-table-column label="隐藏" width="80" align="center">
            <template #default="scope">
              <el-switch
                v-model="scope.row.isHidden"
                :active-value="1"
                :inactive-value="0"
                @change="handleHide(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column label="评论时间" prop="createdAt" width="160" align="center">
            <template #default="scope">
              <span class="text-xs text-slate-400 font-mono">{{ scope.row.createdAt }}</span>
            </template>
          </el-table-column>
          <el-table-column label="管理 ACTIONS" width="180" fixed="right" align="center">
            <template #default="scope">
              <div class="flex items-center justify-center space-x-3">
                <button
                  v-if="scope.row.auditStatus === 0"
                  class="text-[10px] font-bold uppercase tracking-widest text-emerald-600 hover:text-emerald-800"
                  @click="handleAudit(scope.row, 1)"
                >
                  通过
                </button>
                <button
                  v-if="scope.row.auditStatus === 0"
                  class="text-[10px] font-bold uppercase tracking-widest text-red-500 hover:text-red-700"
                  @click="handleAudit(scope.row, 2)"
                >
                  拒绝
                </button>
                <button
                  class="text-[10px] font-bold uppercase tracking-widest text-slate-400 hover:text-red-500"
                  @click="handleDelete(scope.row)"
                >
                  删除
                </button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!-- Pagination -->
      <div class="px-6 py-4 border-t border-slate-100 bg-slate-50 flex items-center justify-between">
        <span class="text-[11px] font-bold text-slate-400 uppercase tracking-wider">
          Total: <span class="text-indigo-600 font-bold px-1">{{ total }}</span> entries
        </span>
        <el-pagination
          v-model:current-page="queryParams.pageNum"
          v-model:page-size="queryParams.pageSize"
          :total="total"
          :page-sizes="[10, 20, 30, 50]"
          layout="prev, pager, next"
          background
          class="!border-none"
          @current-change="getList"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { Search } from '@element-plus/icons-vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import request, { TableDataInfo } from '@/utils/request';

const loading = ref(false);
const commentList = ref<any[]>([]);
const total = ref(0);

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  auditStatus: null,
  content: ''
});

const getList = async () => {
  loading.value = true;
  try {
    const res = await request.get<any, TableDataInfo>('/system/comment/list', { params: queryParams });
    commentList.value = res.rows;
    total.value = res.total;
  } catch (error) {
    console.error('Failed to get comment list:', error);
  } finally {
    loading.value = false;
  }
};

const handleAudit = async (row: any, status: number) => {
  const statusName = status === 1 ? '通过' : '拒绝';
  try {
    await request.put(`/system/comment/audit/${row.id}/${status}`);
    ElMessage.success(`审核已${statusName}`);
    getList();
  } catch (error) {
    console.error('Audit failed:', error);
  }
};

const handleHide = async (row: any) => {
  try {
    // 假设 audit 接口也可以处理显示隐藏，或者有专门的显示隐藏接口
    // 这里使用用户请求中提到的 audit 接口逻辑或类似 PUT 同步状态
    // 如果没有专门接口，通常是 PUT /system/comment 修改整行
    await request.put('/system/comment', { id: row.id, isHidden: row.isHidden });
    ElMessage.success(row.isHidden === 1 ? '评论已隐藏' : '评论已显示');
  } catch (error) {
    row.isHidden = row.isHidden === 1 ? 0 : 1; // 恢复状态
    console.error('Hide toggle failed:', error);
  }
};

const handleDelete = (row: any) => {
  ElMessageBox.confirm('确定要删除这条评论吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    confirmButtonClass: '!bg-red-500 !border-red-500',
  }).then(async () => {
    try {
      await request.delete(`/system/comment/${row.id}`);
      ElMessage.success('删除成功');
      getList();
    } catch (error) {
      console.error('Delete failed:', error);
    }
  });
};

onMounted(() => {
  getList();
});
</script>

<style>
.geometric-select .el-input__wrapper {
  background-color: #f8fafc !important;
  border: 1px solid #e2e8f0 !important;
  box-shadow: none !important;
  border-radius: 10px !important;
}

.el-table {
  --el-table-header-bg-color: #f8fafc;
  --el-table-border-color: #f1f5f9;
}
</style>
