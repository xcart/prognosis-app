<script>
    export let user = null
    export let size = "small" // can also be "large"
</script>

<div class="user-avatar-block {size} {user.login}">
    <img src="{user.avatarUrl}" alt="{user.login} avatar"/>
</div>

<style>
    .user-avatar-block.small {
        width: 32px;
    }

    .user-avatar-block.large {
        width: 48px;
    }

    .user-avatar-block img {
        border-radius: 3px;
        max-width: 100%;
        max-height: 100%;
    }
</style>