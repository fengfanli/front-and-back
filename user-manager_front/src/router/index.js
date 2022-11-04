import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'main',
    component: () => import('../views/Main.vue'),
    children: [
      {
        path: '/user',
        name: 'user',
        component: () => import('../views/user/index.vue')
      },
      {
        path: '/home',
        name: 'home',
        component: () => import('../views/home/index.vue')
      }
    ]
  },
  
]

const router = new VueRouter({
  mode: 'history',
  routes
})
export default router;