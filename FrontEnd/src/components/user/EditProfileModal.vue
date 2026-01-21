<script setup>
import { ref, watch } from 'vue'
import api from '@/api'

const props = defineProps({
  isOpen: Boolean,
  currentDescription: String
})

const emit = defineEmits(['close', 'update-success'])

const description = ref('')
const loading = ref(false)

watch(() => props.isOpen, (newVal) => {
  if (newVal) {
    description.value = props.currentDescription || ''
  }
})

const handleSubmit = async () => {
  loading.value = true
  try {
    const { data } = await api.patch('user/description', {
      description: description.value
    })
    
    // Backend returns ApiResponse<Void>
    if (data.success) {
      emit('update-success', description.value)
      emit('close')
    } else {
        alert('업데이트 실패: ' + (data.error?.message || '알 수 없는 오류'))
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
        
        <h2>한줄소개 변경</h2>

        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label>새로운 소개</label>
            <input type="text" v-model="description" placeholder="나를 표현하는 한 마디" required />
          </div>

          <button type="submit" class="submit-btn" :disabled="loading">
            {{ loading ? '저장 중...' : '저장하기' }}
          </button>
        </form>
      </div>
    </div>
  </Transition>
</template>

<style scoped>
/* Reusing generic modal styles from other modals could be better, but keeping scoped here for safety */
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
  background: none;
  border: none;
  cursor: pointer;
  line-height: 1;
}
.close-btn:hover { color: #333; }

h2 {
  margin-bottom: 30px;
  text-align: center;
  color: var(--color-text);
  font-size: 1.5rem;
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
  border: none;
  cursor: pointer;
}
.submit-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
