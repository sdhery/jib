/*
 * Copyright 2018 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.cloud.tools.jib.ncache;

import com.google.cloud.tools.jib.blob.Blobs;
import com.google.cloud.tools.jib.image.DescriptorDigest;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/** Tests for {@link DefaultCacheWriteEntry}. */
@RunWith(MockitoJUnitRunner.class)
public class DefaultCacheWriteEntryTest {

  @Mock private DescriptorDigest mockSelector;

  @Test
  public void testLayerOnly() throws IOException {
    CacheWriteEntry cacheWriteEntry = DefaultCacheWriteEntry.layerOnly(Blobs.from("layerBlob"));
    Assert.assertEquals("layerBlob", Blobs.writeToString(cacheWriteEntry.getLayerBlob()));
    Assert.assertFalse(cacheWriteEntry.getSelector().isPresent());
    Assert.assertFalse(cacheWriteEntry.getMetadataBlob().isPresent());
  }

  @Test
  public void testWithSelectorAndMetadata() throws IOException {
    CacheWriteEntry cacheWriteEntry =
        DefaultCacheWriteEntry.withSelectorAndMetadata(
            Blobs.from("layerBlob"), mockSelector, Blobs.from("metadataBlob"));
    Assert.assertEquals("layerBlob", Blobs.writeToString(cacheWriteEntry.getLayerBlob()));
    Assert.assertEquals(mockSelector, cacheWriteEntry.getSelector().orElse(null));
    Assert.assertEquals(
        "metadataBlob", Blobs.writeToString(cacheWriteEntry.getMetadataBlob().orElse(null)));
  }
}
