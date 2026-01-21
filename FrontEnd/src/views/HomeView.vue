<script setup>
import { ref, onMounted } from 'vue'
import PostCard from '@/components/post/PostCard.vue'
import api from '@/api'

const posts = ref([])

onMounted(async () => {
  try {
    // PostController is at root /posts, so we override the baseURL
    const { data } = await api.get('/posts', { baseURL: 'http://localhost:8080/api/v1' })
    if (data.success) {
      // Map MypageResponse to PostCard props
      // MypageResponse: title, singleText, image, location, createdAt
      // Missing: id, author
      posts.value = data.data.map((p) => ({
        id: p.id,
        title: p.title,
        excerpt: p.singleText,
        image: p.image,
        location: p.location,
        author: 'Unknown', // API doesn't return author
        date: p.createdAt
      }))
    }
  } catch (e) {
    console.error('Failed to fetch posts', e)
  }
})
</script>

<template>
  <div class="home-view">
    <!-- Hero Section -->
    <section class="hero-section">
      <div class="hero-content">
        <h1>당신의 여행을 기록하세요</h1>
        <p class="subtitle">여블 & 파블과 함께 떠나는 특별한 여행 이야기</p>
      </div>
    </section>

    <div class="container main-content">
      <div class="section-header">
        <h2>최신 여행기</h2>
      </div>
      
      <div class="post-grid">
        <PostCard v-for="post in posts" :key="post.id" :post="post" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.home-view {
  min-height: 100vh;
  background-color: var(--color-bg-soft);
}

.hero-section {
  height: 400px;
  background-image: linear-gradient(rgba(0,0,0,0.3), rgba(0,0,0,0.3)), url('https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?auto=format&fit=crop&w=1600&q=80');
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  color: white;
  margin-bottom: 40px;
}

.hero-content h1 {
  font-size: 3rem;
  font-weight: 800;
  margin-bottom: 15px;
  text-shadow: 0 2px 4px rgba(0,0,0,0.3);
}

.hero-content .subtitle {
  font-size: 1.2rem;
  font-weight: 500;
  opacity: 0.9;
}

.main-content {
  padding-bottom: 60px;
}

.section-header {
  margin-bottom: 30px;
  text-align: center;
}

.section-header h2 {
  font-size: 2rem;
  color: var(--color-text);
  font-weight: 700;
  position: relative;
  display: inline-block;
  padding-bottom: 10px;
}

.section-header h2::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 50px;
  height: 3px;
  background-color: var(--color-primary);
}

.post-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
}

@media (max-width: 768px) {
  .hero-section {
    height: 300px;
  }
  .hero-content h1 {
    font-size: 2rem;
  }
  .post-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
}
</style>
