package com.xcart.prognosis.domain

enum class IssueState {
    Requested, Assigned, Open, InProgress, Waiting, QualityAssurance, HasDefects, ReadyToDeploy, QaPassed, Completed, Canceled
}
