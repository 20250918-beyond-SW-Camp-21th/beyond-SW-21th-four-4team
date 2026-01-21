<script setup>
import { RouterView, useRouter } from 'vue-router'
import NavBar from '@/components/common/NavBar.vue'
import AuthModal from '@/components/auth/AuthModal.vue'
import WriteModal from '@/components/post/WriteModal.vue'
import { ref, onMounted } from 'vue'
import api from '@/api'

const router = useRouter()
const showLogin = ref(false)
const showWrite = ref(false)
const isLogged = ref(false)
const user = ref(null)

onMounted(async () => {
  const token = localStorage.getItem('accessToken')
  if (token) {
    try {
      const { data } = await api.get('user/mypage')
      if (data.success) {
        user.value = data.data
        isLogged.value = true
      }
    } catch (e) {
      console.error('Session restore failed', e)
      localStorage.removeItem('accessToken')
      isLogged.value = false
    }
  }
})

const handleLogin = () => {
  showLogin.value = true
}
const handleWrite = () => {
  showWrite.value = true
}

const onLoginSuccess = async (userData) => {
  isLogged.value = true
  showLogin.value = false
  
  // Fetch user if not provided (since AuthModal no longer provides it)
  if (!userData) {
      try {
        const { data } = await api.get('user/mypage')
        if (data.success) {
           user.value = data.data
        }
      } catch(e) { console.error(e) }
  } else {
      user.value = userData
  }
}

const handleLogout = () => {
  // Call backend logout if needed, otherwise just client side
  // User controller has logout endpoint
  api.post('user/logout').catch(e => console.error(e))
  
  isLogged.value = false
  user.value = null
  localStorage.removeItem('accessToken')
  router.push('/')
}
</script>

<template>
  <NavBar :isLogged="isLogged" @open-login="handleLogin" @open-write="handleWrite" @logout="handleLogout" />
  <AuthModal :isOpen="showLogin" @close="showLogin = false" @login-success="onLoginSuccess" />
  <WriteModal :isOpen="showWrite" @close="showWrite = false" />
  <RouterView />
</template>

<style scoped>
</style>
