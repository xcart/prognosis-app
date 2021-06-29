import App from './App.svelte';
import {updateStateStore} from './actions'
import './global.scss';

updateStateStore(window.initialState)

const app = new App({
    target: document.body,
    props: {
        url: ''
    }
});

export default app;