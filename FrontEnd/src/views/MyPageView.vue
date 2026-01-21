<script setup>
import { ref, onMounted } from 'vue'
import PostCard from '@/components/post/PostCard.vue'
import EditProfileModal from '@/components/user/EditProfileModal.vue'
import api from '@/api'

const user = ref({
  name: '',
  email: '',
  description: '',
  stats: {
    posts: 0,
    followers: 0, // Backend doesn't provide this yet
    following: 0  // Backend doesn't provide this yet
  }
})

const myPosts = ref([])
const showEditModal = ref(false)

const fetchMyPage = async () => {
    try {
        const { data } = await api.get('user/mypage')
        if (data.success) {
            user.value = {
                ...user.value,
                name: data.data.name,
                email: data.data.email,
                description: data.data.description
            }
        }
    } catch (e) {
        console.error('Failed to fetch profile', e)
    }
}

const fetchMyPosts = async () => {
    try {
        const { data } = await api.get('user') // Returns List<MypageResponse>
        if (data.success) {
            myPosts.value = data.data.map((p) => ({
                id: p.id,
                title: p.title,
                excerpt: p.singleText,
                image: p.image,
                location: p.location,
                author: user.value.name,
                date: p.createdAt
            }))
            user.value.stats.posts = myPosts.value.length
        }
    } catch (e) {
        console.error('Failed to fetch posts', e)
    }
}

const onUpdateSuccess = (newDesc) => {
    user.value.description = newDesc
}

onMounted(() => {
    fetchMyPage()
    fetchMyPosts()
})
</script>

<template>
  <div class="my-page">
    <div class="container layout">
      <aside class="sidebar">
        <div class="profile-card">
          <div class="avatar-large">{{ user.name ? user.name.charAt(0) : '?' }}</div>
          <h2>{{ user.name }}</h2>
          <p class="email">{{ user.email }}</p>
          <p class="bio">{{ user.description || '한줄 소개를 입력해주세요.' }}</p>
          
          <div class="stats">
            <div class="stat-item">
              <strong>{{ user.stats.posts }}</strong>
              <span>게시물</span>
            </div>
            <!-- Backend doesn't support followers yet
            <div class="stat-item">
              <strong>{{ user.stats.followers }}</strong>
              <span>팔로워</span>
            </div>
            -->
          </div>
          
          <div class="actions">
            <button class="btn-edit" @click="showEditModal = true">한줄소개 변경</button>
          </div>
        </div>
      </aside>

      <main class="content">
        <h3 class="section-heading">나의 여행 기록</h3>
        <div class="post-list">
          <PostCard v-for="post in myPosts" :key="post.id" :post="post" />
        </div>
      </main>
      
      <EditProfileModal 
        :isOpen="showEditModal" 
        :currentDescription="user.description"
        @close="showEditModal = false"
        @update-success="onUpdateSuccess"
      />
    </div>
  </div>
</template>

<style scoped>
.my-page {
  padding: 40px 0;
  background-color: var(--color-bg-soft);
  min-height: calc(100vh - 70px);
}

.layout {
  display: grid;
  grid-template-columns: 300px 1fr;
  gap: 30px;
}

/* Sidebar Profile */
.sidebar {
  position: sticky;
  top: 100px;
  height: fit-content;
}

.profile-card {
  background: white;
  padding: 30px;
  border-radius: 12px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
  border: 1px solid #eee;
}

.avatar-large {
  width: 100px;
  height: 100px;
  background-color: var(--color-primary);
  color: white;
  font-size: 2.5rem;
  font-weight: 700;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
}

.profile-card h2 {
  margin-bottom: 5px;
  font-size: 1.4rem;
}

.email {
  color: var(--color-text-light);
  font-size: 0.9rem;
  margin-bottom: 15px;
}

.bio {
  font-size: 0.95rem;
  line-height: 1.5;
  margin-bottom: 25px;
  color: var(--color-text);
}

.stats {
  display: flex;
  justify-content: space-around;
  padding: 15px 0;
  border-top: 1px solid #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 20px;
}

.stat-item {
  display: flex;
  flex-direction: column;
}

.stat-item strong {
  font-size: 1.2rem;
  color: var(--color-text);
}

.stat-item span {
  font-size: 0.8rem;
  color: var(--color-text-light);
}

.btn-edit {
  padding: 8px 20px;
  border: 1px solid #ddd;
  background: white;
  border-radius: 20px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.2s;
}
.btn-edit:hover {
  background: #f9f9f9;
  border-color: #ccc;
}

/* Content Area */
.section-heading {
  font-size: 1.5rem;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 2px solid var(--color-primary);
  display: inline-block;
}

.post-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

@media (max-width: 768px) {
  .layout {
    grid-template-columns: 1fr;
  }
  .sidebar {
    position: static;
  }
}
</style>
