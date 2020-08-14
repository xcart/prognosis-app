import {state, errors} from "./stores"
import {start, move, finish} from "./util/progress"
import {modifyUrl} from "./util/addressBar"

const workloadApiBaseurl = "/api/workload"

function buildUrl(urlString, queryParams) {
    let searchParams = new URLSearchParams()
    Object.keys(queryParams).forEach((key) => {
        searchParams.append(key, queryParams[key])
    });
    return urlString + '?' + searchParams.toString()
}

export function performSearch(query) {
    start()
    modifyUrl(buildUrl("/", {query: query}))
    fetch(buildUrl(workloadApiBaseurl, {
        query: query
    }))
        .then(response => {
            move()
            if (response.ok) {
                return response.json();
            }
            throw new Error("Could not perform search, check your query");
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

export function addError(message) {
    errors.update(val => {
        let error = {
            key: "" + Math.random(),
            message: message
        }
        return [...val, error]
    })
}