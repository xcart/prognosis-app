import {state, storedQuery, errors, movedIssues, storedLogin} from "./stores"
import {start, move, finish} from "./util/progress"
import {modifyAddressBar} from "./util/addressBar"
import {get} from 'svelte/store'

const apiBaseurl = "/api"

function buildUrlWithParams(urlString, queryParams) {
  let searchParams = new URLSearchParams()
  Object.keys(queryParams).forEach((key) => {
    searchParams.append(key, queryParams[key])
  });
  return urlString + '?' + searchParams.toString()
}

function csrfToken() {
  const token = document.querySelector('meta[name="_csrf"]').content
  const header = document.querySelector('meta[name="_csrf_header"]').content
  return {
    [header]: token
  }
}

function loadPageState(apiUrl, addressBarUrl, params) {
  start()
  modifyAddressBar(buildUrlWithParams(addressBarUrl, params))
  return fetch(buildUrlWithParams(apiBaseurl + apiUrl, params))
    .then(response => {
      move()
      if (response.ok) {
        return response.json();
      }
      throw new Error("Could not perform page load, check your query");
    })
    .then(result => {
      clearMovedIssues()
      state.set(result)
      finish()
    })
    .catch(reason => {
      addError(reason)
      finish()
    })
}

export function loadWorkloadReport(query = "") {
  storedQuery.set(query)
  return loadPageState("/workload", "/", {query: query})
}

export function reloadUsertasksReport() {
  const user = get(storedLogin)
  const query = get(storedQuery)
  return loadUsertasksReport(user, query)
}

export function loadUsertasksReport(user, query = "") {
  storedLogin.set(user)
  storedQuery.set(query)
  return loadPageState("/usertasks/" + user, "/tasks/" + user, {query: query})
}

export function loadProjectReport(client, query = "") {
  return loadPageState("/projects/" + client, "/projects/" + client, {query: query})
}

export function loadProjectsReport(query = "") {
  return loadPageState("/projects", "/projects", {query: query})
}

export function getRescheduledSwimlane(issueId, shiftAmount) {
  return fetch(buildUrlWithParams(
    apiBaseurl + "/issue/" + issueId + "/reschedule",
    {"shift_amount": shiftAmount}
  ))
    .then(response => {
      if (response.ok) {
        return response.json();
      }
      let message = response.json().errorMessage
      throw new Error("Could not reschedule issue: " + message)
    })
    .then(result => {
      movedIssues.update(current => ({
        ...current,
        [result.id]: result
      }))
    })
    .catch(reason => {
      addError(reason)
    })
}

export function persistMovedIssues() {
  let body = Object.values(get(movedIssues))

  return fetch(apiBaseurl + "/issue/persist", {
    method: "POST",
    headers: {
      'Content-Type': 'application/json',
      ...csrfToken()
    },
    body: JSON.stringify(body)
  })
    .then(response => {
      if (response.ok) {
        return reloadUsertasksReport()
      }
      throw new Error("Could not save changes, see server logs for details")
    })
    .catch(reason => {
      addError(reason)
    })
}

export function clearMovedIssues() {
  movedIssues.set({})
}

export function addError(message) {
  errors.update(val => {
    let error = {
      key: "" + Math.random(),
      message: message
    }
    return [...val, error]
  })
}