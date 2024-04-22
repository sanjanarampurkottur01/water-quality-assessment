import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ocean(
    @PrimaryKey
    @ColumnInfo(name = "station_id")
    val stationId: String,

    @ColumnInfo(name = "station_name")
    val stationName: String,

    @ColumnInfo(name = "full_station_name")
    val fullStationName: String,

    @ColumnInfo(name = "station_number")
    val stationNumber: String,

    @ColumnInfo(name = "station_type")
    val stationType: String,

    val latitude: String,
    val longitude: String,
    val status: String,

    @ColumnInfo(name = "county_name")
    val countyName: String,

    @ColumnInfo(name = "sample_code")
    val sampleCode: String,

    @PrimaryKey
    @ColumnInfo(name = "sample_date")
    val sampleDate: String,

    @ColumnInfo(name = "sample_depth")
    val sampleDepth: String,

    @ColumnInfo(name = "sample_depth_units")
    val sampleDepthUnits: String,

    val parameter: String,
    val result: String,

    @ColumnInfo(name = "reporting_limit")
    val reportingLimit: String,

    val units: String,

    @ColumnInfo(name = "method_name")
    val methodName: String
)
