<script>
  import {state} from '../../stores'

  export let user = null
  export let size = "small" // can also be "large"

  const regex = /^(https?:\/\/|\/\/)/

  const buildUrl = (avatar) => {
    return !regex.test(avatar)
      ? $state.context.youtrackUrl + avatar
      : avatar
  }
</script>

<div class="user-avatar-block {size} {user.login}">
    <img src="{buildUrl(user.avatarUrl)}" alt="{user.login} avatar"/>
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