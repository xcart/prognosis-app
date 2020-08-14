import App from './App.svelte';
import {state} from './stores'
import './global.scss';

state.set(window.initialState)

const app = new App({
    target: document.body,
    props: {
        url: ''
    }
});

export default app;