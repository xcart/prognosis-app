<script>
  import IssueState from "../common/IssueState.svelte"
    export let id = null
    export let issues = null

    $: length = issues ? issues.length : 0;
</script>

<div class="extra-info" id="{id}">
    <div class="issue-count">{length} {#if length != 1}issues{:else}issue{/if}</div>
    <ul class="issues-list">
    {#each issues as issue}
        <li class="issue-item">
            <IssueState state={issue.state} />
            <a class="issue-link" href="https://xcart.myjetbrains.com/youtrack/issue/{issue.idReadable}"
           target="_blank">{issue.idReadable}</a>
        </li>
    {/each}
    </ul>
</div>

<style>
    .issue-item + .issue-item {
        margin-top: var(--list-line-margin);
    }

    .issue-link {
        margin-left: .25rem;
        line-height: 1rem;
        vertical-align: middle;
    }

    .extra-info .issues-list {
        list-style: none;
        margin-top: var(--list-line-margin);
        margin-bottom: 0;
        padding-left: 0;
    }
</style>