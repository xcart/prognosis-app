export function modifyUrl(url, data) {
    window.history.pushState(data, "", url);
}