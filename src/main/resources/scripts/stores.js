import {derived, writable} from "svelte/store"

export const state = writable({})
export const movedIssues = writable({})
export const storedQuery = writable("")
export const storedLogin = writable(null)
export const errors = writable([])

export const canChangeIssues = derived(state, $state => {
  if (typeof $state.context !== "undefined") {
    return !!$state.context.canChangeIssues
  }

  return false
})