import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView
        },
        {
            path: '/post/:id',
            name: 'post-detail',
            component: () => import('../views/PostDetailView.vue')
        },
        {
            path: '/mypage',
            name: 'mypage',
            component: () => import('../views/MyPageView.vue')
        }
    ]
})

export default router
