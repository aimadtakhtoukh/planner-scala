import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

const Default = {template : "<div>Pas là frère</div>"};
const TokenHandling = {template : "<div>Token!</div>"};

const router = new VueRouter({
    routes : [
        {path : '/token/discord', component: TokenHandling},
        {path : '/pasla', component: Default},
    ]
});

export default router