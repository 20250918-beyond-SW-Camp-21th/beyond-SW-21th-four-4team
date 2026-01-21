<script setup>
import { ref } from 'vue'
import api from '@/api'

const props = defineProps({
  isOpen: Boolean
})

const emit = defineEmits(['close', 'post-created'])

const loading = ref(false)
const form = ref({
  title: '',
  location: '',
  content: '',
  singleText: '',
  file: null
})

const handleFileChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    form.value.file = file
  }
}

const handleSubmit = async () => {
  if (!form.value.file) {
    alert('이미지를 업로드해주세요.')
    return
  }

  loading.value = true
  try {
    const formData = new FormData()
    formData.append('title', form.value.title)
    formData.append('location', form.value.location)
    formData.append('text', form.value.content)
    formData.append('singleText', form.value.singleText)
    formData.append('file', form.value.file)

    await api.post('/posts', formData, {
      baseURL: 'http://localhost:8080/api/v1',
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    
    alert('여행기가 발행되었습니다!')
    emit('post-created')
    emit('close')
    // Reset
    form.value = { title: '', location: '', content: '', singleText: '', file: null }
  } catch (error) {
    console.error(error)
    alert('발행 실패: ' + (error.response?.data?.error?.message || error.message))
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
        
        <h2>여행기 공유하기</h2>

        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label>제목</label>
            <input type="text" v-model="form.title" placeholder="나의 멋진 여행..." required />
          </div>

          <div class="form-group">
            <label>장소</label>
            <input type="text" v-model="form.location" placeholder="도시, 국가" required />
          </div>
          
          <div class="form-group">
            <label>한줄 요약 (리스트 노출)</label>
            <input type="text" v-model="form.singleText" placeholder="이 여행을 한마디로 표현한다면?" required />
          </div>

          <div class="form-group">
            <label>커버 이미지</label>
            <input type="file" @change="handleFileChange" accept="image/*" required />
          </div>

          <div class="form-group">
            <label>이야기</label>
            <textarea v-model="form.content" rows="6" placeholder="당신의 모험에 대해 들려주세요..." required></textarea>
          </div>

          <button type="submit" class="submit-btn" :disabled="loading">
            {{ loading ? '발행 중...' : '여행기 발행' }}
          </button>
        </form>
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
  max-width: 600px;
  padding: 40px;
  border-radius: 20px;
  position: relative;
  box-shadow: 0 10px 30px rgba(0,0,0,0.2);
  max-height: 90vh;
  overflow-y: auto;
}

.close-btn {
  position: absolute;
  top: 15px;
  right: 20px;
  font-size: 2rem;
  color: #aaa;
  line-height: 1;
  background: none;
  border: none;
  cursor: pointer;
}
.close-btn:hover { color: #333; }

h2 {
  margin-bottom: 30px;
  text-align: center;
  color: var(--color-text);
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

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s;
  font-family: inherit;
}

.form-group input:focus,
.form-group textarea:focus {
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
  cursor: pointer;
  border: none;
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
