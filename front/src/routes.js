import Vue from 'vue'
import VueRouter from 'vue-router'
import TokenHandling from './components/TokenHandling'

Vue.use(VueRouter);

const Default = {template : "<div>Pas là frère</div>"};

const router = new VueRouter({
    routes : [
        {path : '/token/discord', component: TokenHandling},
        {path : '/pasla', component: Default},
    ],
    mode : 'history'
});

export default router