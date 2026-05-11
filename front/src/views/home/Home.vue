<template>
  <div class="space-y-10 pb-20">
    <!-- Hero Banner -->
    <section class="relative rounded-3xl overflow-hidden h-80 shadow-2xl group">
      <img 
        src="https://images.unsplash.com/photo-1504711432869-efd5971ee142?auto=format&fit=crop&q=80&w=2670" 
        alt="Banner" 
        class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-700" 
      />
      <div class="absolute inset-0 bg-gradient-to-t from-slate-900 via-slate-900/40 to-transparent flex flex-col justify-end p-10">
        <div class="flex items-center space-x-3 mb-4">
          <span class="px-3 py-1 bg-indigo-600 text-white text-[10px] font-bold uppercase tracking-widest rounded-full">Featured</span>
          <span class="text-slate-300 text-xs font-medium uppercase tracking-wider">May 10, 2026</span>
        </div>
        <h2 class="text-5xl font-black text-white mb-2 tracking-tight italic">GEOMETRIC NEWS HUB</h2>
        <p class="text-slate-300 text-lg font-medium max-w-2xl">Discover the world's most innovative stories and professional insights, delivered with geometric precision.</p>
      </div>
    </section>

    <!-- Main Grid -->
    <div class="grid grid-cols-1 lg:grid-cols-12 gap-10">
      <!-- News Feed (Left) -->
      <div class="lg:col-span-8 space-y-8">
        <div class="flex items-center justify-between border-b border-slate-200 pb-4">
          <div class="flex items-center">
            <div class="w-2 h-8 bg-indigo-600 rounded-full mr-4"></div>
            <h3 class="text-2xl font-bold text-slate-800 tracking-tight uppercase">Latest Stories</h3>
          </div>
          <div class="flex bg-slate-100 p-1 rounded-lg">
            <button 
              v-for="tab in ['latest', 'hot']" 
              :key="tab"
              @click="activeTab = tab"
              class="px-5 py-1.5 rounded-md text-[10px] font-bold uppercase tracking-wider transition-all"
              :class="activeTab === tab ? 'bg-white text-indigo-600 shadow-sm' : 'text-slate-400 hover:text-slate-600'"
            >
              {{ tab }}
            </button>
          </div>
        </div>

        <div v-loading="loading" class="space-y-6">
          <div 
            v-for="news in newsList" 
            :key="news.id" 
            class="group bg-white p-5 rounded-2xl border border-slate-200 shadow-sm flex flex-col md:flex-row gap-6 hover:shadow-xl hover:border-indigo-200 transition-all cursor-pointer overflow-hidden"
            @click="goToDetail(news.id)"
          >
            <!-- Image Wrap -->
            <div class="md:w-56 h-40 flex-shrink-0 rounded-xl overflow-hidden relative">
              <img 
                :src="news.coverUrl || 'https://images.unsplash.com/photo-1585829365234-750523078430?q=80&w=800'" 
                class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500" 
              />
              <div class="absolute inset-0 bg-indigo-600/10 opacity-0 group-hover:opacity-100 transition-opacity"></div>
            </div>
            
            <!-- Content Wrap -->
            <div class="flex-1 flex flex-col justify-between py-1">
              <div>
                <div class="flex items-center justify-between mb-2">
                  <span class="px-2 py-0.5 bg-slate-100 text-slate-500 text-[9px] font-bold rounded uppercase tracking-wider group-hover:bg-indigo-50 group-hover:text-indigo-600 transition-colors">
                    {{ news.categoryName || 'General' }}
                  </span>
                  <span class="text-slate-400 text-[10px] font-mono tracking-tighter">{{ news.publishTime?.split(' ')[0] }}</span>
                </div>
                <h4 class="text-xl font-bold text-slate-900 group-hover:text-indigo-600 transition-colors line-clamp-2 leading-tight">
                  {{ news.title }}
                </h4>
                <p class="mt-3 text-slate-500 line-clamp-2 text-sm leading-relaxed">{{ news.summary }}</p>
              </div>
              
              <div class="mt-4 flex items-center justify-between border-t border-slate-50 pt-3">
                <div class="flex items-center space-x-4">
                   <div class="flex items-center text-[10px] font-bold text-slate-400 uppercase tracking-widest">
                     <Eye class="w-3 h-3 mr-1 text-slate-300" />
                     {{ news.readCount || 0 }} Views
                   </div>
                   <div class="flex items-center text-[10px] font-bold text-slate-400 uppercase tracking-widest">
                     <Heart class="w-3 h-3 mr-1 text-slate-300" />
                     {{ news.likeCount || 0 }} Likes
                   </div>
                </div>
                <button class="text-indigo-600 opacity-0 group-hover:opacity-100 transition-all flex items-center text-[10px] font-bold uppercase tracking-widest">
                  Read More <ArrowRight class="w-3 h-3 ml-1" />
                </button>
              </div>
            </div>
          </div>

          <div v-if="newsList.length === 0 && !loading" class="py-20 text-center bg-white rounded-2xl border border-dashed border-slate-200">
            <p class="text-slate-400 font-bold uppercase tracking-widest text-xs">No records found</p>
          </div>
        </div>

        <!-- Pagination -->
        <div v-if="total > queryParams.pageSize" class="flex justify-center pt-6">
          <el-pagination
            v-model:current-page="queryParams.pageNum"
            :page-size="queryParams.pageSize"
            :total="total"
            layout="prev, pager, next"
            background
            class="!border-none"
            @current-change="getList"
          />
        </div>
      </div>

      <!-- Sidebar (Right) -->
      <div class="lg:col-span-4 space-y-10">
        <!-- Categories Card -->
        <div class="bg-slate-900 rounded-2xl p-8 text-white shadow-xl relative overflow-hidden">
          <div class="absolute -top-10 -right-10 w-40 h-40 bg-indigo-500/20 rounded-full blur-3xl"></div>
          <h3 class="text-sm font-bold uppercase tracking-[0.2em] text-indigo-400 mb-6 relative">Browse Categories</h3>
          <div class="flex flex-wrap gap-2 relative">
            <button 
              v-for="cat in categories" 
              :key="cat.id"
              class="px-4 py-2 rounded-lg text-xs font-bold uppercase tracking-wider transition-all border"
              :class="queryParams.categoryId === cat.id ? 'bg-indigo-600 border-indigo-500 text-white' : 'bg-slate-800 hover:bg-indigo-600 border-slate-700 hover:border-indigo-500'"
              @click="handleCategoryFilter(cat.id)"
            >
              {{ cat.categoryName }}
            </button>
          </div>
        </div>

        <!-- Rankings Table -->
        <div class="bg-white border border-slate-200 rounded-2xl p-8 shadow-sm">
          <h3 class="text-xs font-bold uppercase tracking-widest text-slate-400 mb-6 flex items-center">
            <TrendingUp class="w-4 h-4 mr-2 text-indigo-600" />
            Top Trending
          </h3>
          <div class="space-y-6">
            <div 
              v-for="(news, index) in newsList.slice(0, 5)" 
              :key="news.id" 
              class="flex items-start space-x-4 group cursor-pointer"
              @click="goToDetail(news.id)"
            >
              <span 
                class="text-2xl font-black italic shrink-0 w-6 leading-none transition-colors"
                :class="index < 3 ? 'text-indigo-600' : 'text-slate-200 group-hover:text-slate-400'"
              >
                {{ index + 1 }}
              </span>
              <div>
                <p class="text-sm font-bold text-slate-800 group-hover:text-indigo-600 transition-colors line-clamp-2 leading-tight">
                  {{ news.title }}
                </p>
                <div class="flex items-center mt-1 text-[10px] text-slate-400 font-bold uppercase tracking-tighter">
                  {{ news.categoryName }} • {{ news.publishTime?.split(' ')[0] }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Newsletter Geometric Box -->
        <div class="bg-indigo-600 rounded-2xl p-8 text-white shadow-xl shadow-indigo-600/20">
          <div class="w-10 h-10 bg-white/20 rounded-lg flex items-center justify-center mb-6">
            <Mail class="w-5 h-5 text-white" />
          </div>
          <h4 class="text-xl font-bold mb-2">订阅新闻中心</h4>
          <p class="text-indigo-200 text-xs font-medium leading-relaxed mb-6">每日获取最新新闻资讯，直达您的收件箱。</p>
          <div class="space-y-3">
            <el-input placeholder="您的邮箱地址" class="geometric-input-dark" />
            <button class="w-full py-3 bg-white text-indigo-600 rounded-xl text-[11px] font-bold uppercase tracking-widest shadow-lg hover:bg-slate-50 transition-all">
              订阅新闻
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { 
  Eye, 
  Heart, 
  ArrowRight, 
  TrendingUp, 
  Mail 
} from 'lucide-vue-next';
import request, { TableDataInfo, AjaxResult } from '@/utils/request';

const router = useRouter();
const loading = ref(false);
const activeTab = ref('latest');
const newsList = ref<any[]>([]);
const categories = ref<any[]>([]);
const total = ref(0);

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  categoryId: null as number | null,
});

const getList = async () => {
  loading.value = true;
  try {
    const params: any = { ...queryParams };
    if (activeTab.value === 'hot') {
      params.orderByColumn = 'readCount';
      params.isAsc = 'desc';
    }
    const res = await request.get<any, TableDataInfo>('/system/news/public/list', { params });
    newsList.value = res.rows;
    total.value = res.total;
  } catch (error) {
    console.error('Failed to get news:', error);
  } finally {
    loading.value = false;
  }
};

const getCategories = async () => {
  try {
    const res = await request.get<any, AjaxResult>('/system/category/client/list');
    categories.value = Array.isArray(res) ? res : (res.data || res.rows || []);
  } catch (error) {
    console.error('Failed to get categories:', error);
  }
};

const handleCategoryFilter = (id: number) => {
  if (queryParams.categoryId === id) {
    queryParams.categoryId = null;
  } else {
    queryParams.categoryId = id;
  }
  queryParams.pageNum = 1;
  getList();
};

const goToDetail = (id: number) => {
  router.push(`/news/${id}`);
};

onMounted(() => {
  getList();
  getCategories();
});
</script>

<style>
.geometric-input-dark .el-input__wrapper {
  background-color: rgba(255, 255, 255, 0.1) !important;
  border: 1px solid rgba(255, 255, 255, 0.2) !important;
  box-shadow: none !important;
  border-radius: 12px !important;
  padding: 8px 12px !important;
}
.geometric-input-dark .el-input__inner {
  color: white !important;
}
.geometric-input-dark .el-input__inner::placeholder {
  color: rgba(255, 255, 255, 0.5) !important;
}

.el-pagination.is-background .el-pager li:not(.is-disabled).is-active {
  background-color: #4f46e5 !important;
}
</style>

