<script>
  import {state} from '../../stores'

  export let user = null
  export let size = "small" // can also be "large"

  let avatarUrl = null
  let login = null

  const regex = /^(https?:\/\/|\/\/)/

  $: {
    avatarUrl = user ? user.avatarUrl : null
    login = user ? user.login : null
  }

  const buildUrl = (avatar) => {
    if (avatar === null) {
      return "https://www.gravatar.com/avatar/7d1f3cb90562f65ac2921dca7ecf5786.jpg?d=retro&s=80" // Default avatar
    }
    return !regex.test(avatar)
      ? $state.context.youtrackUrl + avatar
      : avatar
  }
</script>

<div class="user-avatar-block {size} {login}">
    <img src="{buildUrl(avatarUrl)}" alt="{login} avatar"/>
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