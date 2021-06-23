import {state, storedQuery, errors, movedIssues} from "./stores"
import {start, move, finish} from "./util/progress"
import {modifyAddressBar} from "./util/addressBar"

const apiBaseurl = "/api"

function buildUrl(urlString, queryParams) {
    let searchParams = new URLSearchParams()
    Object.keys(queryParams).forEach((key) => {
        searchParams.append(key, queryParams[key])
    });
    return urlString + '?' + searchParams.toString()
}

function loadPageState(apiUrl, addressBarUrl, params) {
    start()
    modifyAddressBar(buildUrl(addressBarUrl, params))
    return fetch(buildUrl(apiBaseurl + apiUrl, params))
        .then(response => {
            move()
            if (response.ok) {
                return response.json();
            }
            throw new Error("Could not perform page load, check your query");
        })
        .then(result => {
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

export function loadUsertasksReport(user, query = "") {
    return loadPageState("/usertasks/" + user, "/tasks/" + user, {query: query})
}

export function loadProjectReport(client, query = "") {
    return loadPageState("/projects/" + client, "/projects/" + client, {query: query})
}

export function loadProjectsReport(query = "") {
    return loadPageState("/projects", "/projects", {query: query})
}

export function getRescheduledSwimlane(issueId, shiftAmount) {
    return fetch(buildUrl(apiBaseurl + "/usertasks/" + issueId + "/reschedule", {"shift_amount": shiftAmount}))
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
                [result.id]: {
                    shiftAmount: result.shiftAmount,
                    data: result.data
                }
            }))
        })
        .catch(reason => {
            addError(reason)
        })
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