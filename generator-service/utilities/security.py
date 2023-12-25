def get_user_projects(aws_auth):
    groups = aws_auth.claims["cognito:groups"]
    projects = filter(is_project, groups)
    return map(extract_project_info, projects)


def is_project(group):
    return group.startswith("PROJECT_")


def extract_project_info(project):
    _, name, type_ = project.split("_")
    return {
        "name": name,
        "type": type_,
    }
