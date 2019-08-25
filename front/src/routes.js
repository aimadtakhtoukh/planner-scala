import Vue from 'vue'
import VueRouter from 'vue-router'
import TokenHandling from './components/TokenHandling'
import Subscribe from "./components/Subscribe";
import NotLogged from "./components/NotLogged";
import Redirections from "./components/Redirections";
import Planner from "./components/planner/Planner";

Vue.use(VueRouter);

const router = new VueRouter({
    routes : [
        {path : '/token/discord', component: TokenHandling},
        {path : '/subscribe', component: Subscribe},
        {path : '/not-logged', component: NotLogged},
        {path : '/main', component: Planner},
        {path : '/', component: Redirections},
    ],
    mode : 'history'
});

export default router