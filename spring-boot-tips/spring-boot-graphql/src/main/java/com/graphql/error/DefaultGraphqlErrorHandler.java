package com.graphql.error;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GenericGraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DefaultGraphqlErrorHandler implements GraphQLErrorHandler {
    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> list) {
        final List<GraphQLError> clientErrors = filterGraphQLErrors(list);
        if (clientErrors.size() < list.size()) {
            clientErrors.add(new GenericGraphQLError("Internal Server Error(s) while executing qeury"));

            list.stream()
                    .filter(graphQLError -> !isClientError(graphQLError))
                    .forEach(
                            error -> {
                                if (error instanceof Throwable) {
                                    log.error("Error executing query!", (Throwable) error);
                                } else {
                                    log.error("Error executing query({}):{}", error.getClass().getSimpleName(), error.getMessage());
                                }
                            }
                    );
        }
        return clientErrors;
    }

    protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
        return errors.stream()
                .filter(this::isClientError)
                .collect(Collectors.toList());
    }

    protected boolean isClientError(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            return ((ExceptionWhileDataFetching) error).getException() instanceof GraphQLError;
        }
        return !(error instanceof Throwable);
    }
}
