/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.provider;

import org.gradle.api.internal.tasks.TaskDependencyResolveContext;

import javax.annotation.Nullable;

public abstract class AbstractMappingProvider<OUT, IN> extends AbstractMinimalProvider<OUT> {
    private final Class<OUT> type;
    private final ProviderInternal<? extends IN> provider;

    public AbstractMappingProvider(Class<OUT> type, ProviderInternal<? extends IN> provider) {
        this.type = type;
        this.provider = provider;
    }

    @Nullable
    @Override
    public Class<OUT> getType() {
        return type;
    }

    public ProviderInternal<? extends IN> getProvider() {
        return provider;
    }

    @Override
    public boolean isValueProducedByTask() {
        // Need the content in order to transform the value
        return provider.isContentProducedByTask();
    }

    @Override
    public boolean isContentProducedByTask() {
        return provider.isContentProducedByTask();
    }

    @Override
    public boolean isPresent() {
        return provider.isPresent();
    }

    @Override
    public OUT get() {
        return mapValue(provider.get());
    }

    @Override
    public OUT getOrNull() {
        IN value = provider.getOrNull();
        if (value != null) {
            return mapValue(value);
        }
        return null;
    }

    protected abstract OUT mapValue(IN v);

    @Override
    public boolean maybeVisitBuildDependencies(TaskDependencyResolveContext context) {
        return provider.maybeVisitBuildDependencies(context);
    }

    @Override
    public String toString() {
        return "map(" + provider + ")";
    }
}
