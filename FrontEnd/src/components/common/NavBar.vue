<script setup>
const props = defineProps({
  isLogged: Boolean
})

const emit = defineEmits(['open-login', 'open-write', 'logout'])
</script>

<template>
  <nav class="navbar">
    <div class="container nav-content">
      <router-link to="/" class="logo-link"></router-link>

      <div class="actions">
        <template v-if="isLogged">
          <button @click="$emit('open-write')" class="btn btn-primary">
            글쓰기
          </button>
          <router-link to="/mypage" class="btn btn-text">마이페이지</router-link>
          <button @click="$emit('logout')" class="btn btn-textOrOutline">로그아웃</button>
        </template>
        
        <button v-else @click="$emit('open-login')" class="btn btn-outline">
          로그인
        </button>
      </div>
    </div>
  </nav>
</template>

<style scoped>
.navbar {
  height: 60px;
  /* Navbar에는 '색상'만 남깁니다. */
  background-color: #ffffff; 
  
  border-bottom: 1px solid #d0d0d0;
  position: relative; /* 중요: 내부의 logo-link를 정중앙 배치하기 위한 기준점 */
  top: 0;
  z-index: 100;
  display: flex;
  align-items: center;
  box-shadow: 0 1px 3px rgba(0,0,0,0.05);
}

.nav-content {
  display: flex;
  /* 왼쪽 공간을 비우고 버튼들을 오른쪽 끝으로 밀어줍니다 */
  justify-content: flex-end; 
  align-items: center;
  width: 100%;
  height: 100%;
}

/* [변경점 2] 로고 링크가 이제 배경 이미지를 담당하고, 중앙에 위치합니다 */
.logo-link {
  position: absolute;     /* 네비게이션 바 흐름에서 벗어나서 */
  left: 50%;              /* 왼쪽에서 50% 지점으로 이동 */
  transform: translateX(-50%); /* 정확한 정중앙으로 보정 */
  
  height: 100%;           /* 높이 꽉 채우기 */
  width: 400px;           /* 클릭 가능한 가로 너비 (이미지 비율에 맞춰 조절 가능) */
  
  /* --- 배경 이미지 설정 이동 --- */
  background-image: url('@/assets/nav_image.png');
  background-repeat: no-repeat;
  background-position: center center;
  background-size: contain; 
  
  /* [가로로 살짝 늘리고 싶다면?]
     위의 background-size: contain; 대신 아래를 사용하세요.
     예: 가로 80%, 세로 90% (수치는 취향껏 조절)
  */
  /* background-size: 80% 90%; */
  
  z-index: 10; /* 버튼보다 아래에 위치하되 클릭은 가능하게 */
  cursor: pointer;
}

.actions {
  display: flex;
  gap: 10px;
  align-items: center;
  z-index: 20; /* 로고보다 위에 있어서 버튼 클릭 방해 안 받게 설정 */
  position: relative; 
}

/* 기존 버튼 스타일 그대로 유지 */
.btn {
  padding: 6px 16px;
  border-radius: 4px;
  font-weight: 500;
  font-size: 0.85rem;
  transition: all 0.2s;
  cursor: pointer;
}

.btn-text {
  color: var(--color-text);
  font-weight: 600;
}
.btn-text:hover {
  color: var(--color-primary);
}

.btn-primary {
  background-color: var(--color-primary);
  color: white;
  border: 1px solid var(--color-primary);
}
.btn-primary:hover {
  background-color: #e04e53;
  border-color: #e04e53;
}

.btn-outline {
  border: 1px solid var(--color-text);
  color: var(--color-text);
}
.btn-outline:hover {
  background-color: var(--color-text);
  color: white;
}

.btn-textOrOutline {
  color: var(--color-text);
  border: 1px solid transparent;
  padding: 6px 12px;
}
.btn-textOrOutline:hover {
  border-color: var(--color-text);
}
</style>