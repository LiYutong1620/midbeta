<template>
  <div class="space-y-6">
    <!-- Header Area -->
    <div class="bg-white border border-slate-200 rounded-xl shadow-sm p-6 flex flex-col md:flex-row md:items-center justify-between gap-4">
      <div>
        <h3 class="font-bold text-slate-800 text-lg">新闻资讯管理</h3>
        <p class="text-xs text-slate-400 mt-1 uppercase tracking-wider font-bold">Manage and publish site news</p>
      </div>
      <div class="flex items-center space-x-3">
        <el-input
          v-model="queryParams.title"
          placeholder="搜索新闻标题"
          class="!w-64"
          clearable
          @keyup.enter="getList"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <button
          class="px-6 py-2 bg-indigo-600 text-white rounded-lg text-[11px] font-bold uppercase tracking-wider shadow-lg shadow-indigo-600/20 hover:bg-indigo-700 transition-all flex items-center"
          @click="handleAdd"
        >
          <el-icon class="mr-2"><Plus /></el-icon>
          发布新闻
        </button>
      </div>
    </div>

    <!-- Table Area -->
    <div class="bg-white border border-slate-200 rounded-xl shadow-sm overflow-hidden flex flex-col min-h-[400px]">
      <div class="flex-1 overflow-x-auto">
        <el-table
          v-loading="loading"
          :data="newsList"
          style="width: 100%"
          class="!text-slate-600"
        >
          <el-table-column label="ID" prop="id" width="80" align="center">
            <template #default="scope">
              <span class="font-mono text-xs text-slate-400">#{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column label="标题" prop="title" min-width="200" show-overflow-tooltip>
            <template #default="scope">
              <span class="font-bold text-slate-800">{{ scope.row.title }}</span>
            </template>
          </el-table-column>
          <el-table-column label="分类" prop="categoryName" width="120" align="center">
            <template #default="scope">
              <span class="px-2 py-0.5 bg-indigo-50 text-indigo-600 text-[10px] font-bold rounded uppercase tracking-wider ring-1 ring-indigo-100">
                {{ scope.row.categoryName || '默认分类' }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="发布时间" prop="publishTime" width="180" align="center">
            <template #default="scope">
              <span class="text-xs text-slate-400 font-mono">{{ scope.row.publishTime }}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" width="100" align="center">
            <template #default="scope">
              <span v-if="scope.row.publishStatus === 1" class="flex items-center justify-center text-[10px] uppercase font-bold text-emerald-500">
                <span class="w-1.5 h-1.5 bg-emerald-500 rounded-full mr-2"></span>
                已发布
              </span>
              <span v-else class="flex items-center justify-center text-[10px] uppercase font-bold text-slate-400">
                <span class="w-1.5 h-1.5 bg-slate-300 rounded-full mr-2"></span>
                草稿
              </span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150" fixed="right" align="center">
            <template #default="scope">
              <div class="flex items-center justify-center space-x-3">
                <button
                  class="text-[10px] font-bold uppercase tracking-widest text-indigo-600 hover:text-indigo-800"
                  @click="handleUpdate(scope.row)"
                >
                  编辑
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

    <!-- News Form Drawer -->
    <NewsForm ref="newsFormRef" @success="getList" />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { Search, Plus } from '@element-plus/icons-vue';
import NewsForm from './components/NewsForm.vue';
import request, { TableDataInfo } from '@/utils/request';

const loading = ref(false);
const newsList = ref<any[]>([]);
const total = ref(0);
const newsFormRef = ref<any>();

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  title: ''
});

const getList = async () => {
  loading.value = true;
  try {
    const res = await request.get<any, TableDataInfo>('/system/news/list', { params: queryParams });
    newsList.value = res.rows;
    total.value = res.total;
  } catch (error) {
    console.error('Failed to get news list:', error);
  } finally {
    loading.value = false;
  }
};

const handleAdd = () => {
  newsFormRef.value?.open();
};

const handleUpdate = (row: any) => {
  newsFormRef.value?.open(row.id);
};

const handleDelete = (row: any) => {
  ElMessageBox.confirm(`确定要删除标题为 "${row.title}" 的新闻吗？`, '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    confirmButtonClass: '!bg-red-500 !border-red-500',
  }).then(async () => {
    try {
      await request.delete(`/system/news/${row.id}`);
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
.el-pagination.is-background .el-pager li:not(.is-disabled).is-active {
  background-color: #4f46e5 !important;
}
.el-table {
  --el-table-header-bg-color: #f8fafc;
  --el-table-border-color: #f1f5f9;
}
</style>
