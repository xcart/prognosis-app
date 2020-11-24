import tippy from "tippy.js"
import equal from "fast-deep-equal"

let container = document.createDocumentFragment()

function buildComponent(component, props) {
    let id = 'tooltip' + Math.random() * 10000000
    let instanceProps = {
        id: id
    }
    Object.assign(instanceProps, props)

    new component({
        target: container,
        props: instanceProps
    })

    return container.getElementById(id)
}

function initializeTippy(node, parameters) {
    if (parameters) {
        let component = buildComponent(parameters.component, parameters.props)
        return tippy(node, {
            content: component,
            interactive: !!parameters.interactive,
            placement: 'auto-start',
            appendTo: document.body,
            theme: 'light'
        })
    }
    return null
}

export function tooltip(node, parameters) {
    let instance = initializeTippy(node, parameters);

    let oldProps = parameters

    return {
        update(newProps) {
            if (!equal(newProps, oldProps)) {
                if (instance) {
                    instance.destroy()
                }

                instance = initializeTippy(node, newProps)
            }
            oldProps = newProps
        },
        destroy() {
            if (instance) {
                instance.destroy()
            }
            oldProps = null
        }
    }
}