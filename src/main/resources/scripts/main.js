import App from './App.svelte';
import './global.scss';

const app = new App({
	target: document.body,
	props: {
		url: '',
		state: window.initialState
	}
});

export default app;