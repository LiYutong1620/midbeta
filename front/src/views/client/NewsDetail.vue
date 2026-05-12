<template>
  <div class="max-w-4xl mx-auto space-y-10 pb-20 font-sans">
    <div v-loading="loading">
      <!-- Article Header -->
      <header class="space-y-6 pt-10">
        <div class="flex items-center space-x-4">
          <span class="px-3 py-1 bg-indigo-50 text-indigo-600 text-[10px] font-bold uppercase tracking-widest rounded-lg border border-indigo-100">
            {{ news.categoryName || 'GENERAL' }}
          </span>
          <span class="text-slate-400 text-xs font-mono">{{ news.publishTime }}</span>
        </div>
        <h1 class="text-4xl md:text-5xl font-black text-slate-900 tracking-tight leading-tight italic">
          {{ news.title }}
        </h1>
        <div class="flex items-center justify-between py-6 border-y border-slate-100">
          <div class="flex items-center space-x-6">
            <div class="flex items-center text-[11px] font-bold text-slate-400 uppercase tracking-[0.2em]">
              <Eye class="w-4 h-4 mr-2 text-slate-300" />
              {{ news.readCount || 0 }} Views
            </div>
            <div class="flex items-center text-[11px] font-bold text-slate-400 uppercase tracking-[0.2em]">
              <Heart class="w-4 h-4 mr-2" :class="isLiked ? 'text-rose-500 fill-rose-500' : 'text-slate-300'" />
              {{ news.likeCount || 0 }} Likes
            </div>
          </div>
          <button 
            class="flex items-center space-x-2 px-6 py-2 rounded-xl transition-all duration-300 font-bold uppercase tracking-widest text-[10px]"
            :class="isLiked ? 'bg-rose-50 text-rose-500 border border-rose-100' : 'bg-white border border-slate-200 text-slate-500 hover:border-indigo-600 hover:text-indigo-600 shadow-sm'"
            @click="handleLike"
          >
            <Heart class="w-3.5 h-3.5" :class="{ 'fill-rose-500': isLiked }" />
            <span>{{ isLiked ? 'Liked' : 'Like This Story' }}</span>
          </button>
        </div>
      </header>

      <!-- Featured Image -->
      <div v-if="news.coverUrl" class="mt-8 rounded-3xl overflow-hidden shadow-2xl h-[450px]">
        <img :src="getFullUrl(news.coverUrl)" class="w-full h-full object-cover" />
      </div>

      <!-- Article Content -->
      <article class="mt-12 prose prose-lg prose-indigo max-w-none text-slate-700 leading-relaxed font-medium">
        <div v-html="news.content"></div>
      </article>
    </div>

    <!-- Recommendations -->
    <section v-if="recommendations.length > 0" class="pt-16 border-t border-slate-100">
      <div class="flex items-center mb-8">
        <div class="w-1 h-6 bg-indigo-600 rounded-full mr-3"></div>
        <h3 class="text-xl font-bold text-slate-900 tracking-tight uppercase">You Might Also Like</h3>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div 
          v-for="rec in recommendations" 
          :key="rec.id" 
          class="group cursor-pointer space-y-3"
          @click="goToNews(rec.id)"
        >
          <div class="aspect-video rounded-2xl overflow-hidden bg-slate-100 border border-slate-100 transition-hover group-hover:shadow-lg">
            <img :src="rec.coverUrl ? getFullUrl(rec.coverUrl) : 'https://images.unsplash.com/photo-1585829365234-750523078430?q=80&w=800'" class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
          </div>
          <p class="text-xs font-bold text-indigo-600 uppercase tracking-widest">{{ rec.categoryName || 'NEWS' }}</p>
          <h4 class="font-bold text-slate-800 group-hover:text-indigo-600 transition-colors line-clamp-2 leading-snug">
            {{ rec.title }}
          </h4>
        </div>
      </div>
    </section>

    <!-- Comments Section -->
    <section class="mt-16 bg-slate-50 rounded-3xl p-10 border border-slate-200 shadow-sm">
      <div class="flex items-center justify-between mb-8">
        <h3 class="text-xl font-bold text-slate-900 tracking-tight flex items-center">
          <MessageSquare class="w-5 h-5 mr-3 text-indigo-600" />
          COMMENTS ({{ comments.length }})
        </h3>
      </div>

      <!-- Comment Input -->
      <div class="mb-10 bg-white p-6 rounded-2xl border border-slate-200 shadow-sm transition-focus-within focus-within:ring-2 focus-within:ring-indigo-600/20">
        <el-input
          v-model="newComment"
          type="textarea"
          :rows="4"
          placeholder="Leave your thoughts..."
          class="geometric-input-naked"
        />
        <div class="mt-4 flex justify-end">
          <button 
            :disabled="!newComment.trim() || submittingComment"
            class="px-8 py-2.5 bg-indigo-600 text-white rounded-xl text-[11px] font-bold uppercase tracking-widest shadow-lg shadow-indigo-600/20 hover:bg-indigo-700 transition-all disabled:opacity-50"
            @click="submitComment"
          >
            {{ submittingComment ? 'Posting...' : 'Post Comment' }}
          </button>
        </div>
      </div>

      <!-- List of Comments -->
      <div v-if="comments.length > 0" class="space-y-6">
        <div v-for="c in comments" :key="c.id" class="p-6 bg-white rounded-2xl border border-slate-100 flex space-x-4">
          <div class="w-10 h-10 rounded-xl bg-slate-900 flex items-center justify-center shrink-0 uppercase font-black text-indigo-400 text-lg italic">
            {{ c.nickName ? c.nickName.charAt(0) : 'G' }}
          </div>
          <div class="flex-1 space-y-1">
            <div class="flex items-center justify-between">
              <span class="font-bold text-slate-800 text-sm">{{ c.nickName || 'Guest' }}</span>
              <span class="text-[10px] text-slate-400 font-mono">{{ c.createdAt?.split(' ')[0] }}</span>
            </div>
            <p class="text-slate-600 text-sm leading-relaxed">{{ c.content }}</p>
          </div>
        </div>
      </div>
      <div v-else class="text-center py-10">
        <p class="text-slate-400 font-medium italic text-sm">No comments yet. Be the first to join the conversation!</p>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { getFullUrl } from '@/utils/image';
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { 
  Eye, 
  Heart, 
  MessageSquare, 
  ArrowRight 
} from 'lucide-vue-next';
import { ElMessage } from 'element-plus';
import request, { AjaxResult, TableDataInfo } from '@/utils/request';

const route = useRoute();
const router = useRouter();
const loading = ref(true);
const isLiked = ref(false);
const news = ref<any>({});
const recommendations = ref<any[]>([]);
const comments = ref<any[]>([]);
const newComment = ref('');
const submittingComment = ref(false);

const getNewsDetail = async (id: number) => {
  loading.value = true;
  try {
    const res = await request.get<any, any>(`/system/news/public/${id}`);
    news.value = res.data || res;
    loading.value = false;
    getRecommendations(id);
    getComments(id);
    recordBrowse(id); // 记录浏览次数
  } catch (error) {
    console.error('Failed to get news detail:', error);
  }
};

const recordBrowse = async (id: number) => {
  try {
    await request.post('/system/browse', { newsId: id });
  } catch (e) {
    // 静默失败
  }
};

const getRecommendations = async (id: number) => {
  try {
    const res = await request.get<any, AjaxResult>(`/system/news/recommend/${id}`);
    recommendations.value = Array.isArray(res) ? res : (res.data || []);
  } catch (error) {
    console.error('Failed to get recommendations:', error);
  }
};

const getComments = async (id: number) => {
  try {
    const res = await request.get<any, TableDataInfo>(`/system/comment/list`, {
      params: { newsId: id, auditStatus: 1 }
    });
    comments.value = res.rows || [];
  } catch (error) {
    console.error('Failed to get comments:', error);
  }
};

const handleLike = async () => {
  const targetStatus = isLiked.value ? 0 : 1;
  try {
    await request.post('/system/like', {
      newsId: news.value.id,
      likeStatus: targetStatus
    });
    
    isLiked.value = !isLiked.value;
    if (isLiked.value) {
      news.value.likeCount = (news.value.likeCount || 0) + 1;
      ElMessage.success('Thanks for the like!');
    } else {
      news.value.likeCount = Math.max(0, (news.value.likeCount || 0) - 1);
    }
  } catch (error) {
    ElMessage.error('点赞失败');
  }
};

const submitComment = async () => {
  if (!newComment.value.trim()) return;
  submittingComment.value = true;
  try {
    await request.post('/system/comment', {
      newsId: news.value.id,
      content: newComment.value
    });
    ElMessage.success('评论成功，等待管理员审核');
    newComment.value = '';
  } catch (error) {
    console.error('Comment submission failed:', error);
  } finally {
    submittingComment.value = false;
  }
};

const goToNews = (id: number) => {
  router.push(`/news/${id}`);
};

// 监听路由 ID 变化
watch(() => route.params.id, (newId) => {
  if (newId) {
    getNewsDetail(Number(newId));
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
});

onMounted(() => {
  if (route.params.id) {
    getNewsDetail(Number(route.params.id));
  }
});
</script>

<style>
.geometric-input-naked .el-textarea__inner {
  border: none !important;
  box-shadow: none !important;
  font-family: inherit;
  font-size: 1rem;
  color: #1e293b;
  padding: 0 !important;
}

/* Prose refinements */
.prose h2, .prose h3 {
  font-style: italic;
  font-weight: 900;
  letter-spacing: -0.025em;
  color: #0f172a;
}
.prose p {
  font-weight: 500;
}
</style>
