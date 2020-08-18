import nprogress from "nprogress"

nprogress.configure({ showSpinner: false, trickleSpeed: 100 })

export function start() {
    nprogress.start()
}

export function finish() {
    nprogress.done()
}

export function move() {
    nprogress.inc()
}