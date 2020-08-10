import App from './App.svelte';
import { state } from './stores'
import './global.scss';

Object.prototype.toMap = function() {
	return new Map(Object.entries(this))
}

state.set(window.initialState)

const app = new App({
	target: document.body,
	props: {
		url: ''
	}
});

export default app;