package org.sample;

import com.eatthepath.jeospatial.util.SimpleGeospatialPoint;
import com.eatthepath.jeospatial.vptree.VPTree;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.spatial.SpatialStrategy;
import org.apache.lucene.spatial.prefix.RecursivePrefixTreeStrategy;
import org.apache.lucene.spatial.prefix.tree.GeohashPrefixTree;
import org.apache.lucene.spatial.query.SpatialArgs;
import org.apache.lucene.spatial.query.SpatialOperation;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.shape.ShapeFactory;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class MyBenchmark {

    // Lucene
    private static final String COORDINATES_FIELD = "coordinates";
    private static final int GEO_PRECISION_LEVEL = 5;
    private static final double NEARBY_RADIUS_DEGREE = DistanceUtils.dist2Degrees(
            50, DistanceUtils.EARTH_MEAN_RADIUS_KM);

    private final Directory directory = new RAMDirectory();
    private final IndexWriterConfig iwConfig = new IndexWriterConfig();
    private IndexWriter indexWriter = null;
    private IndexSearcher indexSearcher = null;
    private final SpatialContext spatialCxt = SpatialContext.GEO;
    private final ShapeFactory shapeFactory = spatialCxt.getShapeFactory();
    private final SpatialStrategy coordinatesStrategy = new RecursivePrefixTreeStrategy(
            new GeohashPrefixTree(spatialCxt, GEO_PRECISION_LEVEL),
            COORDINATES_FIELD);

    // Jeospatial
    private VPTree<SimpleGeospatialPoint> jeospatialPoints = new VPTree<>();

    public MyBenchmark() {
        try {
            indexWriter = new IndexWriter(directory, iwConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Setup
    public void init() throws IOException {
        var r = new Random();
        for (int i = 0; i < 3000; i++) {
            double latitude = ThreadLocalRandom.current().nextDouble(50.4D, 51.4D);
            double longitude = ThreadLocalRandom.current().nextDouble(8.2D, 11.2D);

            Document doc = new Document();
            doc.add(new StoredField("id", r.nextInt()));
            var point = shapeFactory.pointXY(longitude, latitude);
            for (var field : coordinatesStrategy.createIndexableFields(point)) {
                doc.add(field);
            }
            doc.add(new StoredField(coordinatesStrategy.getFieldName(), latitude + ":" + longitude));
            indexWriter.addDocument(doc);

            jeospatialPoints.add(new MyGeospatialPoint(latitude, longitude));
        }
        indexWriter.forceMerge(1);
        indexWriter.close();
        final IndexReader indexReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(indexReader);
    }

    private SimpleGeospatialPoint createRandomPoint() {
        final double latitude = ThreadLocalRandom.current().nextDouble(50.4D, 51.4D);
        final double longitude = ThreadLocalRandom.current().nextDouble(8.2D, 11.2D);
        return new MyGeospatialPoint(latitude, longitude);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 0)
    @Measurement(iterations = 3)
    public void benchLucene() {
        double latitude = ThreadLocalRandom.current().nextDouble(50.4D, 51.4D);
        double longitude = ThreadLocalRandom.current().nextDouble(8.2D, 11.2D);
        final var spatialArgs = new SpatialArgs(SpatialOperation.IsWithin,
                                                shapeFactory.circle(longitude, latitude, NEARBY_RADIUS_DEGREE));
        final Query q = coordinatesStrategy.makeQuery(spatialArgs);
        try {
            final TopDocs topDocs = indexSearcher.search(q, 1);
            if (topDocs.totalHits == 0) {
                return;
            }
            var doc = indexSearcher.doc(topDocs.scoreDocs[0].doc);
            var coordinates = doc.getField(COORDINATES_FIELD).stringValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 0)
    @Measurement(iterations = 3)
    public void benchJeospatial() {
        var neighbor = jeospatialPoints.getNearestNeighbor(createRandomPoint(), 50 * 1000);
        var n = neighbor.getLatitude();
    }
}
