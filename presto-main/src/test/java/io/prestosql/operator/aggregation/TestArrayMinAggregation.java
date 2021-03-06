/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.operator.aggregation;

import com.google.common.collect.ImmutableList;
import io.prestosql.spi.block.Block;
import io.prestosql.spi.block.BlockBuilder;
import io.prestosql.spi.type.ArrayType;
import io.prestosql.spi.type.Type;

import java.util.List;

import static io.prestosql.spi.type.BigintType.BIGINT;
import static io.prestosql.util.StructuralTestUtil.arrayBlockOf;

public class TestArrayMinAggregation
        extends AbstractTestAggregationFunction
{
    @Override
    protected Block[] getSequenceBlocks(int start, int length)
    {
        ArrayType arrayType = new ArrayType(BIGINT);
        BlockBuilder blockBuilder = arrayType.createBlockBuilder(null, length);
        for (int i = start; i < start + length; i++) {
            arrayType.writeObject(blockBuilder, arrayBlockOf(BIGINT, i));
        }
        return new Block[] {blockBuilder.build()};
    }

    @Override
    protected List<Long> getExpectedValue(int start, int length)
    {
        if (length == 0) {
            return null;
        }
        return ImmutableList.of((long) start);
    }

    @Override
    protected String getFunctionName()
    {
        return "min";
    }

    @Override
    protected List<Type> getFunctionParameterTypes()
    {
        return ImmutableList.of(new ArrayType(BIGINT));
    }
}
