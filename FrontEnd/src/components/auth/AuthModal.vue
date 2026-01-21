<script setup>
import { ref } from 'vue'
import api from '@/api'

const props = defineProps({
  isOpen: Boolean
})

const emit = defineEmits(['close', 'login-success'])

const isLoginMode = ref(true)
const loading = ref(false)

import { watch } from 'vue'
watch(() => props.isOpen, (newVal) => {
  if (newVal) isLoginMode.value = true
})

const form = ref({
  name: '',
  email: '',
  password: '',
  introduction: ''
})

const handleSubmit = async () => {
  loading.value = true
  try {
    let tokenResponse
    if (isLoginMode.value) {
      const { data } = await api.post('auth/login', {
        email: form.value.email,
        password: form.value.password
      })
      tokenResponse = data
    } else {
      const { data } = await api.post('auth/signup', {
        email: form.value.email,
        username: form.value.name,
        password: form.value.password,
        description: form.value.introduction
      })
      tokenResponse = data
    }

    if (tokenResponse.success) {
      const accessToken = tokenResponse.data.accessToken
      localStorage.setItem('accessToken', accessToken)

      // Removed user/mypage fetch as requested
      emit('login-success', null)
      emit('close')
      // Reset form
      form.value = { name: '', email: '', password: '', introduction: '' }
    } else {
        alert('요청 실패: ' + (tokenResponse.error?.message || '알 수 없는 오류'))
    }
  } catch (error) {
    console.error(error)
    alert('오류 발생: ' + (error.response?.data?.error?.message || error.message))
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <Transition name="fade">
    <div v-if="isOpen" class="modal-overlay" @click.self="$emit('close')">
      <div class="modal-content">
        <button class="close-btn" @click="$emit('close')">&times;</button>
        
        <div class="modal-header">
          <h2>{{ isLoginMode ? '로그인' : '회원가입' }}</h2>
        </div>

        <form @submit.prevent="handleSubmit" class="auth-form">
          <div v-if="!isLoginMode" class="form-group">
            <label>이름</label>
            <input type="text" v-model="form.name" placeholder="홍길동" required />
          </div>

          <div v-if="!isLoginMode" class="form-group">
            <label>한줄소개</label>
            <input type="text" v-model="form.introduction" placeholder="나를 표현하는 한 마디" />
          </div>

          <div class="form-group">
            <label>이메일</label>
            <input type="email" v-model="form.email" placeholder="example@email.com" required />
          </div>

          <div class="form-group">
            <label>비밀번호</label>
            <input type="password" v-model="form.password" placeholder="••••••••" required />
          </div>

          <button type="submit" class="submit-btn" :disabled="loading">
            {{ loading ? '처리 중...' : (isLoginMode ? '로그인' : '계정 만들기') }}
          </button>
        </form>

        <div class="auth-switch">
          <p v-if="isLoginMode">
            계정이 없으신가요? 
            <a href="#" @click.prevent="isLoginMode = false">회원가입</a>
          </p>
          <p v-else>
            이미 계정이 있으신가요? 
            <a href="#" @click.prevent="isLoginMode = true">로그인</a>
          </p>
        </div>
      </div>
    </div>
  </Transition>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  backdrop-filter: blur(5px);
}

.modal-content {
  background: white;
  width: 90%;
  max-width: 400px;
  padding: 40px;
  border-radius: 20px;
  position: relative;
  box-shadow: 0 10px 30px rgba(0,0,0,0.2);
}

.close-btn {
  position: absolute;
  top: 15px;
  right: 20px;
  font-size: 2rem;
  color: #aaa;
  line-height: 1;
}
.close-btn:hover { color: #333; }

.modal-header {
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
}

.modal-header h2 {
  font-size: 1.8rem;
  color: var(--color-text);
  margin: 0;
  text-align: center;
  width: 100%;
}

.auth-switch {
  margin-top: 20px;
  text-align: center;
  font-size: 0.9rem;
  color: var(--color-text-light);
}
.auth-switch a {
  color: var(--color-primary);
  font-weight: 600;
  text-decoration: underline;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-size: 0.9rem;
  margin-bottom: 8px;
  color: var(--color-text-light);
}

.form-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.form-group input:focus {
  outline: none;
  border-color: var(--color-primary);
}

.submit-btn {
  width: 100%;
  padding: 14px;
  background-color: var(--color-primary);
  color: white;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  margin-top: 10px;
}
.submit-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

/* Transitions */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
