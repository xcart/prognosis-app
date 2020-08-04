import App from './App.svelte';
import 'bootstrap/scss/bootstrap.scss';

const app = new App({
	target: document.body,
	props: {
		url: '',
		state: window.initialState
	}
});

export default app;