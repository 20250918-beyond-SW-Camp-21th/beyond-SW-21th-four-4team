<script setup>
defineProps({
  post: {
    type: Object,
    required: true
  }
})
</script>

<template>

  <router-link :to="{ name: 'post-detail', params: { id: post.id } }" class="post-item">
    <div class="thumbnail-wrapper" v-if="post.image">
      <img :src="post.image" :alt="post.title" loading="lazy" />
    </div>
    <div class="content">
      <h3 class="title">{{ post.title }}</h3>
      <p class="excerpt">{{ post.excerpt }}</p>
      <div class="meta">
        <span class="date">{{ post.date }}</span>
        <span class="separator">·</span>
        <span class="location" v-if="post.location">{{ post.location }}</span>
      </div>
    </div>
  </router-link>
</template>

<style scoped>
.post-item {
  display: flex;
  flex-direction: column;
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
  transition: transform 0.2s, box-shadow 0.2s;
  height: 100%;
  text-decoration: none;
  color: inherit;
}
.post-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 20px rgba(0,0,0,0.1);
}

.thumbnail-wrapper {
  width: 100%;
  height: 200px;
  overflow: hidden;
}

.thumbnail-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s;
}

.post-item:hover .thumbnail-wrapper img {
  transform: scale(1.05);
}

.content {
  padding: 20px;
  display: flex;
  flex-direction: column;
  flex: 1;
}

.title {
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--color-text);
  margin-bottom: 10px;
  line-height: 1.4;
}

.excerpt {
  font-size: 0.95rem;
  color: #666;
  margin-bottom: 20px;
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  flex: 1;
}

.meta {
  font-size: 0.85rem;
  color: #999;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 15px;
  border-top: 1px solid #f0f0f0;
}

.location {
  color: var(--color-primary);
  font-weight: 600;
}

@media (max-width: 600px) {
  .thumbnail-wrapper {
    height: 180px;
  }
}
</style>
