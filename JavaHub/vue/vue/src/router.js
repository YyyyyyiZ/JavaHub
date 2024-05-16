import { createRouter, createWebHistory } from 'vue-router';
import App from './App.vue';

const routes = [
    {
        path: '/',
        name: 'app',
        component: App,
        children: [
            {
                path: '',
                name: 'dashboard',
                component: () => import('./components/Dashboard.vue')
            },
            {
                path: '/wrongSet',
                name: 'wrongSet',
                component: () => import('./components/WrongSet.vue')
            },
            {
                path: '/collec',
                name: 'collection',
                component: () => import('./components/Collection.vue')
            },
            {
                path: '/test',
                name: 'test',
                component: () => import('./components/Test.vue'),
            },
            {
                path: '/user',
                name: 'user',
                component: () => import('./components/User.vue'),
            },
            {
                path: '/testDetail',
                name: 'testDetail',
                component: () => import('./components/TestDetail.vue'),
            },
            {
                path: '/exer',
                name: 'exer',
                component: () => import('./components/Exer.vue'),
            },
            {
                path: '/single',
                name: 'single',
                component: () => import('./components/SingleCh.vue'),
            },
            {
                path: '/multi',
                name: 'multi',
                component: () => import('./components/MultiCh.vue'),
            },
            {
                path: '/fill',
                name: 'fill',
                component: () => import('./components/Fill.vue'),
            },
            {
                path: '/judge',
                name: 'judge',
                component: () => import('./components/Judge.vue'),
            },
            {
                path: '/doTest',
                name: 'doTest',
                component: () => import('./components/TestDo.vue'),
            },
            {
                path: '/learn',
                name: 'learn',
                component: () => import('./components/Learn.vue'),
            },
            {
                path: '/timer',
                name: 'timer',
                component: () => import('./components/Timer.vue'),
            },
            {
                path: '/guide',
                name: 'guide',
                component: () => import('./components/Guide.vue'),
            },
        ]
    },
    {
        path: '/pswd',
        name: 'pswd',
        component: () => import('./pages/Pswd.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('./pages/Login.vue')
    },
    {
        path: '/signUp',
        name: 'signUp',
        component: () => import('./pages/SignUp.vue')
    },
    {
        path: '/testad',
        name: 'testad',
        component: () => import('./pages/test.vue'),
        children: [
            {
                path: '/admin',
                name: 'admin',
                component: () => import('./pages/Admin.vue')//默认为userad
            },
            {
                path: '/secad',
                name: 'secad',
                component: () => import('./components/Secad.vue'),
            },
            {
                path: '/knowad',
                name: 'knowad',
                component: () => import('./components/Knowad.vue'),
            },
            {
                path: '/exerad',
                name: 'exerad',
                component: () => import('./components/Exerad.vue'),
            },
        ]
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router;
