<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import api from '@/api'

const route = useRoute()
const post = ref(null)
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  const id = route.params.id
  loading.value = true
  try {
    const { data } = await api.get(`/posts/${id}`, { baseURL: 'http://localhost:8080/api/v1' })
    if (data.success) {
      const p = data.data
      post.value = {
        id: id,
        title: p.title,
        content: p.text,
        image: p.image,
        location: p.location,
        author: p.username || `User ${p.userId}`, 
        date: p.date, 
        tags: [] 
      }
    } else {
        error.value = '글을 찾을 수 없습니다.'
    }
  } catch (e) {
    console.error(e)
    error.value = '오류가 발생했습니다.'
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="post-detail-view">
    <div v-if="loading" class="loading">여행기 불러오는 중...</div>
    
    <div v-else-if="post" class="content-wrapper">
      <header class="post-header" :style="{ backgroundImage: `url(${post.image})` }">
        <div class="overlay"></div>
        <div class="container header-content">
          <span class="location">📍 {{ post.location }}</span>
          <h1>{{ post.title }}</h1>
          <div class="meta">
            <span>작성자: {{ post.author }}</span>
            <span>•</span>
            <span>{{ post.date }}</span>
          </div>
        </div>
      </header>

      <article class="container post-body">
        <div class="post-text">
          <p v-for="(paragraph, index) in post.content.split('\n\n')" :key="index">
            {{ paragraph }}
          </p>
        </div>
        
        <div class="tags">
          <span v-for="tag in post.tags" :key="tag" class="tag">#{{ tag }}</span>
        </div>
        
        <div class="author-bio">
          <div class="author-avatar">{{ post.author.charAt(0) }}</div>
          <div class="bio-text">
            <h3>{{ post.author }}</h3>
            <p>여행을 사랑하는 스토리텔러. 한 번에 한 도시씩 세상을 탐험합니다.</p>
          </div>
        </div>
      </article>
    </div>

    <div v-else class="not-found container">
      <h2>글을 찾을 수 없습니다.</h2>
    </div>
  </div>
</template>

<style scoped>
.loading {
  text-align: center;
  padding: 100px;
  font-size: 1.5rem;
  color: var(--color-text-light);
}

.post-header {
  height: 60vh;
  position: relative;
  background-size: cover;
  background-position: center;
  color: white;
  display: flex;
  align-items: center;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to bottom, rgba(0,0,0,0.2), rgba(0,0,0,0.6));
}

.header-content {
  position: relative;
  z-index: 1;
  width: 100%;
}

.location {
  display: inline-block;
  background: rgba(255,255,255,0.2);
  backdrop-filter: blur(5px);
  padding: 5px 15px;
  border-radius: 20px;
  font-weight: 600;
  margin-bottom: 20px;
  text-transform: uppercase;
  font-size: 0.9rem;
  letter-spacing: 1px;
}

h1 {
  font-size: 3.5rem;
  font-weight: 800;
  margin-bottom: 20px;
  line-height: 1.2;
}

.meta {
  display: flex;
  gap: 15px;
  font-size: 1.1rem;
  opacity: 0.9;
}

.post-body {
  padding: 60px 20px;
  max-width: 800px;
}

.post-text p {
  font-size: 1.25rem;
  line-height: 1.8;
  color: var(--color-text);
  margin-bottom: 30px;
}

.tags {
  margin-top: 50px;
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.tag {
  background: var(--color-bg-soft);
  padding: 8px 15px;
  border-radius: 8px;
  color: var(--color-text-light);
  font-size: 0.9rem;
}

.author-bio {
  margin-top: 60px;
  padding-top: 40px;
  border-top: 1px solid #eee;
  display: flex;
  align-items: center;
  gap: 20px;
}

.author-avatar {
  width: 60px;
  height: 60px;
  background: var(--color-primary);
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  font-weight: bold;
}

.bio-text h3 {
  margin-bottom: 5px;
}
.bio-text p {
  color: var(--color-text-light);
}

@media (max-width: 768px) {
  h1 { font-size: 2.5rem; }
  .post-header { height: 50vh; }
}
</style>
