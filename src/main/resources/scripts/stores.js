import { writable } from "svelte/store"

export const state = writable({})
export const storedQuery = writable("")
export const errors = writable([])