<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome</title>
</head>

<body>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" controller="usuario">Usuários</g:link></li>
        <li><g:link class="create" controller="papel">Papéis</g:link></li>
        <li><g:link class="create" controller="funcionalidade">Funcionalidades</g:link></li>
    </ul>
</div>

<h1>Welcome</h1>
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<g:seLogado>
    Seja bem vindo.

</g:seLogado>
</body>
</html>