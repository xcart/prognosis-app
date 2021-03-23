package com.xcart.prognosis.domain

enum class IssueState {
    Waiting,
    Scheduled,
    Submitted,
    Assigned,
    Open,
    InProgress,
    QualityAssurance,
    HasDefects,
    QaPassed,
    OnReview,
    Completed,
    Canceled
}
