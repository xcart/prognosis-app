<script>
    import Notification from "./Notification.svelte"
    import { errors } from "../stores"

    function createToggleHandler(key) {
        return function (event) {
            let callback = event.detail
            callback()
            errors.set($errors.filter(item => item.key !== key))
        }
    }
</script>

<div class="error-handler">
    {#each $errors as error}
    <Notification title="Error" on:toggle={createToggleHandler(error.key)}>
        {error.message}
    </Notification>
    {/each}
</div>

<style>
    .error-handler {
        z-index: 100;
        position: absolute;
        top: 0;
        right: 0;
        padding: 2rem;
    }
</style>