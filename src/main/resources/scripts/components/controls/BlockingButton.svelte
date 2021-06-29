<script>
  import { createEventDispatcher } from 'svelte';

  export let type = "success"

  let disabled = false

	const dispatch = createEventDispatcher();

  function toggleDisabled() {
    disabled = !disabled
  }

  function doAction() {
    toggleDisabled()
    dispatch('click', toggleDisabled)
  }
</script>

<button type="button" class="btn btn-{type}" on:click|preventDefault={doAction} class:disabled={disabled} disabled={disabled}>
  <slot></slot>
</button>