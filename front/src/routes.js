import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

const TokenHandling = {template : "<div>Token!</div>"};

const router = new VueRouter({
    routes : [
        {path : '/**', component: TokenHandling}
    ]
});

export default router