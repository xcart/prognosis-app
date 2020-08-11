<script>
    export let id = null
    export let issues = null

    let length = issues ? issues.length : 0;
</script>

<div class="extra-info" id="{id}">
    <div class="issue-count">{length} {#if length != 1}issues{:else}issue{/if}</div>
    <ul class="issues-list">
    {#each issues as issue}
        <li class="issue-item">
            <a class="issue-link" href="https://xcart.myjetbrains.com/youtrack/issue/{issue.idReadable}"
           target="_blank">{issue.idReadable}</a>
        </li>
    {/each}
    </ul>
</div>

<style>
    .extra-info .issues-list {
        list-style: none;
        margin-bottom: 0;
        padding-left: 0;
    }
</style>