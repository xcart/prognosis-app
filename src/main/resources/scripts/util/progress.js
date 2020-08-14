import nprogress from "nprogress"

nprogress.configure({ showSpinner: false, trickleSpeed: 1600 })

export function start() {
    nprogress.start()
}

export function finish() {
    nprogress.done()
}

export function move() {
    nprogress.inc()
}