<script>
    import { Toast, ToastBody, ToastHeader } from 'sveltestrap';
    import {createEventDispatcher} from "svelte";

    export let title = null
    let isOpen = true;

    const dispatch = createEventDispatcher()

    function toggle() {
        dispatch('toggle', performToggle)
    }

    function performToggle() {
        isOpen = !isOpen;
    }

    setTimeout(toggle, 5000)
</script>

<Toast {isOpen}>
    <ToastHeader {toggle}>{title}</ToastHeader>
    <ToastBody>
        <slot></slot>
    </ToastBody>
</Toast>

<style>
    :global(.toast) {
        min-width: 200px;
    }
</style>