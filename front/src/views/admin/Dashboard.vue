<template>
  <div class="space-y-8 pb-12">
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
      <div v-for="stat in stats" :key="stat.label" 
        class="group p-5 rounded-xl border transition-all duration-300 shadow-sm flex flex-col justify-between h-32"
        :class="stat.highlight ? 'bg-indigo-600 border-indigo-700 shadow-indigo-200' : 'bg-white border-slate-200 hover:border-indigo-300'"
      >
        <div class="flex items-center justify-between">
          <div class="text-[10px] font-bold uppercase tracking-widest" :class="stat.highlight ? 'text-indigo-200' : 'text-slate-400'">
            {{ stat.label }}
          </div>
          <component :is="stat.icon" class="w-4 h-4 opacity-50" :class="stat.highlight ? 'text-white' : 'text-slate-400'" />
        </div>
        <div class="text-3xl font-light tracking-tight" :class="stat.highlight ? 'text-white' : 'text-indigo-600'">
          {{ stat.value }}
        </div>
      </div>
    </div>

    <div class="bg-white border border-slate-200 rounded-xl shadow-sm flex flex-col overflow-hidden">
      <div class="p-6 border-b border-slate-100 flex items-center justify-between bg-white">
        <div>
          <h3 class="font-bold text-slate-800 text-lg">待审核评论</h3>
          <p class="text-xs text-slate-400 mt-1 uppercase tracking-wider font-bold">Recent Pending Comments</p>
        </div>
        <div class="flex space-x-2">
          <button 
            class="px-4 py-2 bg-indigo-600 text-white rounded-lg text-[11px] font-bold uppercase tracking-wider shadow-lg shadow-indigo-600/20 hover:bg-indigo-700 transition-colors"
            @click="handleBatchAudit(1)"
            :disabled="selectedComments.length === 0"
          >
            批量通过
          </button>
        </div>
      </div>
      
      <div class="overflow-x-auto">
        <el-table
          v-loading="loading"
          :data="recentComments"
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="50" />
          <el-table-column label="序号" width="80" align="center">
            <template #default="scope">
              <span class="font-mono text-xs text-slate-400">#{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="用户" prop="nickName" width="120">
            <template #default="scope">
              <div class="flex items-center space-x-3">
                <div class="w-7 h-7 rounded-full bg-slate-100 flex items-center justify-center border border-slate-200 text-slate-500 font-bold text-[10px]">
                  {{ (scope.row.nickName || 'U').charAt(0) }}
                </div>
                <span class="font-bold text-sm text-slate-700">{{ scope.row.nickName || '未知' }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="内容" prop="content" min-width="200" show-overflow-tooltip>
            <template #default="scope">
              <p class="text-sm text-slate-500 line-clamp-1 max-w-xs group-hover:text-slate-700 transition-colors">{{ scope.row.content }}</p>
            </template>
          </el-table-column>
          <el-table-column label="状态" width="100" align="center">
            <template #default="scope">
              <span class="px-2 py-0.5 bg-orange-50 text-orange-600 text-[10px] font-bold rounded uppercase tracking-wider ring-1 ring-orange-200">Pending</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150" align="right">
            <template #default="scope">
              <div class="flex items-center justify-end space-x-3">
                <button class="text-[10px] font-bold uppercase tracking-widest text-emerald-600 hover:text-emerald-800" @click="handleAudit(scope.row, 1)">通过</button>
                <button class="text-[10px] font-bold uppercase tracking-widest text-red-500 hover:text-red-700" @click="handleAudit(scope.row, 2)">拒绝</button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div v-if="commentTotal > queryParams.pageSize" class="px-6 py-4 border-t border-slate-100 bg-slate-50 flex items-center justify-between">
        <span class="text-[11px] font-bold text-slate-400 uppercase tracking-wider">Showing {{ (queryParams.pageNum - 1) * queryParams.pageSize + 1 }} to {{ Math.min(queryParams.pageNum * queryParams.pageSize, commentTotal) }} of <span class="text-indigo-600">{{ commentTotal }}</span> entries</span>
        <el-pagination
          v-model:current-page="queryParams.pageNum"
          v-model:page-size="queryParams.pageSize"
          :total="commentTotal"
          layout="prev, pager, next"
          background
          small
          @current-change="getCommentList"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue';
import { 
  TrendingUp, 
  Newspaper, 
  Users, 
  MessageSquare,
} from 'lucide-vue-next';
import { ElMessage } from 'element-plus';
import request, { TableDataInfo } from '@/utils/request';

const loading = ref(false);
const recentComments = ref<any[]>([]);
const selectedComments = ref<any[]>([]);
const commentTotal = ref(0);

const newsCount = ref(0);
const userCount = ref(0);
const readCount = ref(0);
const pendingCommentCount = ref(0);

const queryParams = reactive({
  pageNum: 1,
  pageSize: 5,
  auditStatus: 0,
});

const stats = computed(() => [
  { label: '总阅读数', value: readCount.value.toLocaleString(), icon: TrendingUp, highlight: false },
  { label: '新闻总数', value: newsCount.value, icon: Newspaper, highlight: false },
  { label: '注册用户数', value: userCount.value, icon: Users, highlight: false },
  { label: '待审核评论', value: pendingCommentCount.value, icon: MessageSquare, highlight: true },
]);

const getStats = async () => {
  try {
    const newsRes: any = await request.get('/system/news/list', { params: { pageNum: 1, pageSize: 1 } });
    newsCount.value = newsRes.total || 0;
    let totalRead = 0;
    const statRes: any = await request.get('/system/statistics/list', { params: { pageNum: 1, pageSize: 1000 } });
    if (statRes.rows) {
      statRes.rows.forEach((s: any) => { totalRead += (s.readCount || 0); });
    }
    readCount.value = totalRead;
    const userRes: any = await request.get('/system/user/list', { params: { pageNum: 1, pageSize: 1 } });
    userCount.value = userRes.total || 0;
    const commentRes: any = await request.get('/system/comment/list', { params: { pageNum: 1, pageSize: 1, auditStatus: 0 } });
    pendingCommentCount.value = commentRes.total || 0;
  } catch (e) {
    console.error('Failed to get dashboard stats:', e);
  }
};

const getCommentList = async () => {
  loading.value = true;
  try {
    const res = await request.get<any, TableDataInfo>('/system/comment/list', { params: queryParams });
    recentComments.value = res.rows || [];
    commentTotal.value = res.total || 0;
  } catch (e) {
    console.error('Failed to get comments:', e);
  } finally {
    loading.value = false;
  }
};

const handleSelectionChange = (val: any[]) => {
  selectedComments.value = val;
};

const handleAudit = async (row: any, status: number) => {
  try {
    await request.put(`/system/comment/audit/${row.id}/${status}`);
    ElMessage.success(status === 1 ? '审核通过' : '已拒绝');
    getCommentList();
    getStats();
  } catch (e) {
    console.error('Audit failed:', e);
  }
};

const handleBatchAudit = async (status: number) => {
  if (selectedComments.value.length === 0) return;
  try {
    for (const c of selectedComments.value) {
      await request.put(`/system/comment/audit/${c.id}/${status}`);
    }
    ElMessage.success('批量操作成功');
    getCommentList();
    getStats();
  } catch (e) {
    console.error('Batch audit failed:', e);
  }
};

onMounted(() => {
  getStats();
  getCommentList();
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
