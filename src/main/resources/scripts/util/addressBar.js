export function modifyAddressBar(url, title = "", data = null) {
    window.history.replaceState(data, title, url);
}