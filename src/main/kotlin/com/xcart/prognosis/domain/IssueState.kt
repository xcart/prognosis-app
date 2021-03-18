package com.xcart.prognosis.domain

enum class IssueState {
    Scheduled, Submitted, Assigned, Open, InProgress, Waiting, QualityAssurance, HasDefects, ReadyToDeploy, QaPassed, Completed, Canceled
}
